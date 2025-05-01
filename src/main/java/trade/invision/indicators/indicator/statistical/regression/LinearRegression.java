package trade.invision.indicators.indicator.statistical.regression;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.cumulative.CumulativeSum;
import trade.invision.indicators.indicator.statistical.regression.LinearRegressionResult.LinearRegressionResultBuilder;
import trade.invision.num.Num;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static trade.invision.indicators.indicator.cumulative.CumulativeSum.cumulativeSum;
import static trade.invision.indicators.indicator.meta.indicator.CurrentIndex.currentIndex;
import static trade.invision.indicators.indicator.operation.unary.UnaryOperation.unaryOperation;
import static trade.invision.indicators.indicator.statistical.regression.LinearRegressionResultType.INTERCEPT;
import static trade.invision.indicators.indicator.statistical.regression.LinearRegressionResultType.NEXT_Y;
import static trade.invision.indicators.indicator.statistical.regression.LinearRegressionResultType.R2;
import static trade.invision.indicators.indicator.statistical.regression.LinearRegressionResultType.RSS;
import static trade.invision.indicators.indicator.statistical.regression.LinearRegressionResultType.SLOPE;
import static trade.invision.indicators.indicator.statistical.regression.LinearRegressionResultType.TSS;
import static trade.invision.indicators.indicator.statistical.regression.LinearRegressionResultType.Y;

/**
 * {@link LinearRegression} is a {@link Num} {@link Indicator} to provide the statistical best fit using the
 * least-squares linear regression model over a <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Simple_linear_regression">Wikipedia</a>
 */
public class LinearRegression extends Indicator<LinearRegressionResult> {

    /**
     * Gets {@link LinearRegressionResult#getSlope} from {@link #linearRegression(Indicator, Set, int)}.
     */
    public static Indicator<Num> linearRegressionSlope(Indicator<Num> indicator, int length) {
        return unaryOperation(LinearRegressionResult::getSlope, linearRegression(indicator, Set.of(SLOPE), length));
    }

    /**
     * Gets {@link LinearRegressionResult#getIntercept} from {@link #linearRegression(Indicator, Set, int)}.
     */
    public static Indicator<Num> linearRegressionIntercept(Indicator<Num> indicator, int length) {
        return unaryOperation(LinearRegressionResult::getIntercept,
                linearRegression(indicator, Set.of(INTERCEPT), length));
    }

    /**
     * Gets {@link LinearRegressionResult#getY} from {@link #linearRegression(Indicator, Set, int)}.
     */
    public static Indicator<Num> linearRegressionY(Indicator<Num> indicator, int length) {
        return unaryOperation(LinearRegressionResult::getY, linearRegression(indicator, Set.of(Y), length));
    }

    /**
     * Gets {@link LinearRegressionResult#getNextY} from {@link #linearRegression(Indicator, Set, int)}.
     */
    public static Indicator<Num> linearRegressionNextY(Indicator<Num> indicator, int length) {
        return unaryOperation(LinearRegressionResult::getNextY, linearRegression(indicator, Set.of(NEXT_Y), length));
    }

    /**
     * Gets {@link LinearRegressionResult#getRss} from {@link #linearRegression(Indicator, Set, int)}.
     */
    public static Indicator<Num> linearRegressionRss(Indicator<Num> indicator, int length) {
        return unaryOperation(LinearRegressionResult::getRss, linearRegression(indicator, Set.of(RSS), length));
    }

    /**
     * Gets {@link LinearRegressionResult#getTss} from {@link #linearRegression(Indicator, Set, int)}.
     */
    public static Indicator<Num> linearRegressionTss(Indicator<Num> indicator, int length) {
        return unaryOperation(LinearRegressionResult::getTss, linearRegression(indicator, Set.of(TSS), length));
    }

    /**
     * Gets {@link LinearRegressionResult#getR2} from {@link #linearRegression(Indicator, Set, int)}.
     */
    public static Indicator<Num> linearRegressionR2(Indicator<Num> indicator, int length) {
        return unaryOperation(LinearRegressionResult::getR2, linearRegression(indicator, Set.of(R2), length));
    }

    /**
     * Gets a {@link LinearRegression}.
     *
     * @param indicator   the {@link Indicator}
     * @param resultTypes the {@link LinearRegressionResultType} {@link Set}
     * @param length      the number of values to look back at
     */
    public static synchronized LinearRegression linearRegression(Indicator<Num> indicator,
            Set<LinearRegressionResultType> resultTypes, int length) {
        final CacheKey cacheKey = new CacheKey(indicator, length);
        LinearRegression linearRegression = CACHE.getIfPresent(cacheKey);
        if (linearRegression == null) {
            linearRegression = new LinearRegression(indicator, resultTypes, length);
            CACHE.put(cacheKey, linearRegression);
        } else {
            linearRegression.resultTypes.addAll(resultTypes);
            linearRegression.purgeCache();
        }
        return linearRegression;
    }

    private static final Cache<CacheKey, LinearRegression> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final Indicator<Num> indicator;
    private final Set<LinearRegressionResultType> resultTypes;
    private final int length;
    private final CumulativeSum sumX;
    private final CumulativeSum sumY;

    protected LinearRegression(Indicator<Num> indicator, Set<LinearRegressionResultType> resultTypes, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        checkArgument(!resultTypes.isEmpty(), "'resultTypes' must not be empty!");
        this.indicator = indicator.caching();
        this.resultTypes = new HashSet<>(resultTypes);
        this.length = length;
        sumX = cumulativeSum(currentIndex(series), length);
        sumY = cumulativeSum(indicator, length);
    }

    @Override
    protected LinearRegressionResult calculate(long index) {
        // Calculation reference: https://introcs.cs.princeton.edu/java/97data/LinearRegression.java.html
        final long startIndex = max(0, index - length + 1);
        final long observations = index - startIndex + 1;
        final Num xBar = sumX.getValue(index).divide(observations);
        final Num yBar = sumY.getValue(index).divide(observations);
        Num xxBar = numOfZero();
        Num xyBar = numOfZero();
        for (long indicatorIndex = startIndex; indicatorIndex <= index; indicatorIndex++) {
            final Num xDelta = numOf(indicatorIndex).subtract(xBar);
            xxBar = xxBar.add(xDelta.square());
            xyBar = xyBar.add(xDelta.multiply(indicator.getValue(indicatorIndex).subtract(yBar)));
        }
        final LinearRegressionResultBuilder result = LinearRegressionResult.builder();
        final Num slope = xyBar.divide(xxBar).ifNaN(numOfZero());
        result.slope(slope);
        final Num intercept = yBar.subtract(slope.multiply(xBar));
        result.intercept(intercept);
        if (resultTypes.contains(Y)) {
            result.y(slope.multiply(index).add(intercept));
        }
        if (resultTypes.contains(NEXT_Y)) {
            result.nextY(slope.multiply(index + 1).add(intercept));
        }
        if (resultTypes.contains(RSS) || resultTypes.contains(TSS) || resultTypes.contains(R2)) {
            Num rss = numOfZero();
            Num tss = numOfZero();
            for (long indicatorIndex = startIndex; indicatorIndex <= index; indicatorIndex++) {
                final Num y = indicator.getValue(indicatorIndex);
                final Num fit = slope.multiply(indicatorIndex).add(intercept);
                rss = rss.add(fit.subtract(y).square());
                tss = tss.add(y.subtract(yBar).square());
            }
            result.rss(rss);
            result.tss(tss);
            if (resultTypes.contains(R2)) {
                result.r2(numOfOne().subtract(rss.divide(tss).ifNaN(numOfZero())));
            }
        }
        return result.build();
    }
}
