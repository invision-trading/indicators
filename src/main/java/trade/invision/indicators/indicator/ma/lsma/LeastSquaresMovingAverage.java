package trade.invision.indicators.indicator.ma.lsma;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.statistical.regression.LinearRegression;
import trade.invision.indicators.indicator.statistical.regression.LinearRegressionResultType;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicator.statistical.regression.LinearRegression.linearRegressionY;

/**
 * {@link LeastSquaresMovingAverage} is a {@link Num} {@link Indicator} to provide a Least Squares Moving Average (LSMA)
 * over a <code>length</code> of values. Also known as the Time Series Moving Average (TSMA). This {@link Indicator}
 * yields {@link LinearRegressionResultType#Y} from {@link LinearRegression}.
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=LstSqrMA.htm">FM Labs</a>
 */
public class LeastSquaresMovingAverage extends Indicator<Num> {

    /**
     * @see #leastSquaresMovingAverage(Indicator, int)
     */
    public static LeastSquaresMovingAverage lsma(Indicator<Num> indicator, int length) {
        return leastSquaresMovingAverage(indicator, length);
    }

    /**
     * Gets a {@link LeastSquaresMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static LeastSquaresMovingAverage leastSquaresMovingAverage(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new LeastSquaresMovingAverage(indicator, length));
    }

    private static final Cache<CacheKey, LeastSquaresMovingAverage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final Indicator<Num> linearRegressionY;

    protected LeastSquaresMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        linearRegressionY = linearRegressionY(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        return linearRegressionY.getValue(index);
    }
}
