package trade.invision.indicators.indicators.statistical.regression;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.cumulative.CumulativeSum;
import trade.invision.indicators.indicators.meta.indicator.CurrentIndex;
import trade.invision.indicators.indicators.statistical.regression.LinearRegressionResult.LinearRegressionResultBuilder;
import trade.invision.num.Num;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static trade.invision.indicators.indicators.statistical.regression.LinearRegressionResultType.NEXT_Y;
import static trade.invision.indicators.indicators.statistical.regression.LinearRegressionResultType.R2;
import static trade.invision.indicators.indicators.statistical.regression.LinearRegressionResultType.RSS;
import static trade.invision.indicators.indicators.statistical.regression.LinearRegressionResultType.TSS;
import static trade.invision.indicators.indicators.statistical.regression.LinearRegressionResultType.Y;

/**
 * {@link LinearRegression} is a {@link Num} {@link Indicator} to provide the statistical best fit using the
 * least-squares linear regression model over a <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Simple_linear_regression">Wikipedia</a>
 */
public class LinearRegression extends Indicator<LinearRegressionResult> {

    /**
     * Convenience static method for {@link #LinearRegression(Indicator, Set, int)}.
     */
    public static LinearRegression linearRegression(Indicator<Num> indicator,
            Set<LinearRegressionResultType> resultTypes, int length) {
        return new LinearRegression(indicator, resultTypes, length);
    }

    private final Indicator<Num> indicator;
    private final Set<LinearRegressionResultType> resultTypes;
    private final int length;
    private final CumulativeSum sumX;
    private final CumulativeSum sumY;

    /**
     * Instantiates a new {@link LinearRegression}.
     *
     * @param indicator   the {@link Indicator}
     * @param resultTypes the {@link LinearRegressionResultType} {@link Set}
     * @param length      the number of values to look back at
     */
    public LinearRegression(Indicator<Num> indicator, Set<LinearRegressionResultType> resultTypes, int length) {
        super(indicator.getSeries(), length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        checkArgument(!resultTypes.isEmpty(), "'resultTypes' must not be empty!");
        this.indicator = indicator.caching();
        this.resultTypes = resultTypes;
        this.length = length;
        sumX = new CumulativeSum(new CurrentIndex(series), length);
        sumY = new CumulativeSum(indicator, length);
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
