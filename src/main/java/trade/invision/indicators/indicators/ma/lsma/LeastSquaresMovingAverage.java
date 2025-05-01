package trade.invision.indicators.indicators.ma.lsma;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.statistical.regression.LinearRegression;
import trade.invision.indicators.indicators.statistical.regression.LinearRegressionResult;
import trade.invision.indicators.indicators.statistical.regression.LinearRegressionResultType;
import trade.invision.num.Num;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.statistical.regression.LinearRegression.linearRegression;
import static trade.invision.indicators.indicators.statistical.regression.LinearRegressionResultType.Y;

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

    private final Indicator<LinearRegressionResult> linearRegression;

    protected LeastSquaresMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        linearRegression = linearRegression(indicator, Set.of(Y), length);
    }

    @Override
    protected Num calculate(long index) {
        return linearRegression.getValue(index).getY();
    }
}
