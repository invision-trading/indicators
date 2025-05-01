package trade.invision.indicators.indicator.ma.lsma;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.statistical.regression.LinearRegressionResultType;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicator.statistical.regression.LinearRegression.linearRegressionNextY;

/**
 * {@link PredictiveLeastSquaresMovingAverage} is a {@link Num} {@link Indicator} to provide a predictive Least Squares
 * Moving Average (PLSMA) over a <code>length</code> of values. This is the same as {@link LeastSquaresMovingAverage},
 * but with {@link LinearRegressionResultType#NEXT_Y}.
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=LstSqrMA.htm">FM Labs</a>
 */
public class PredictiveLeastSquaresMovingAverage extends Indicator<Num> {

    /**
     * @see #predictiveLeastSquaresMovingAverage(Indicator, int)
     */
    public static PredictiveLeastSquaresMovingAverage plsma(Indicator<Num> indicator, int length) {
        return predictiveLeastSquaresMovingAverage(indicator, length);
    }

    /**
     * Gets a {@link PredictiveLeastSquaresMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static PredictiveLeastSquaresMovingAverage predictiveLeastSquaresMovingAverage(Indicator<Num> indicator,
            int length) {
        return CACHE.get(new CacheKey(indicator, length),
                key -> new PredictiveLeastSquaresMovingAverage(indicator, length));
    }

    private static final Cache<CacheKey, PredictiveLeastSquaresMovingAverage>
            CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final Indicator<Num> linearRegressionNextY;

    protected PredictiveLeastSquaresMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        linearRegressionNextY = linearRegressionNextY(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        return linearRegressionNextY.getValue(index);
    }
}
