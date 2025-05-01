package trade.invision.indicators.indicators.ma.ema;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.ma.ema.ExponentialMovingAverage.exponentialMovingAverage;

/**
 * {@link DoubleExponentialMovingAverage} is a {@link Num} {@link Indicator} to provide a Double Exponential Moving
 * Average (DEMA) over a <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/d/double-exponential-moving-average.asp">Investopedia</a>
 */
public class DoubleExponentialMovingAverage extends Indicator<Num> {

    /**
     * @see #doubleExponentialMovingAverage(Indicator, int)
     */
    public static DoubleExponentialMovingAverage dema(Indicator<Num> indicator, int length) {
        return doubleExponentialMovingAverage(indicator, length);
    }

    /**
     * Calls {@link #doubleExponentialMovingAverage(Indicator, int, Num)} with <code>smoothing</code> set to
     * <code>2</code>.
     */
    public static DoubleExponentialMovingAverage doubleExponentialMovingAverage(Indicator<Num> indicator, int length) {
        return doubleExponentialMovingAverage(indicator, length, indicator.getSeries().getNumFactory().two());
    }

    /**
     * @see #doubleExponentialMovingAverage(Indicator, int, Num)
     */
    public static DoubleExponentialMovingAverage dema(Indicator<Num> indicator, int length, Num smoothing) {
        return doubleExponentialMovingAverage(indicator, length, smoothing);
    }

    /**
     * Gets a {@link DoubleExponentialMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param smoothing the smoothing factor (typically 2)
     */
    public static DoubleExponentialMovingAverage doubleExponentialMovingAverage(Indicator<Num> indicator, int length,
            Num smoothing) {
        return CACHE.get(new CacheKey(indicator, length, smoothing),
                key -> new DoubleExponentialMovingAverage(indicator, length, smoothing));
    }

    private static final Cache<CacheKey, DoubleExponentialMovingAverage> CACHE =
            Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
        Num smoothing;
    }

    private final ExponentialMovingAverage ema;
    private final ExponentialMovingAverage emaOfEma;

    protected DoubleExponentialMovingAverage(Indicator<Num> indicator, int length, Num smoothing) {
        super(indicator.getSeries(), length * 2);
        checkArgument(length > 0, "'length' must be greater than zero!");
        ema = exponentialMovingAverage(indicator, length, smoothing);
        emaOfEma = exponentialMovingAverage(ema, length, smoothing);
    }

    @Override
    protected Num calculate(long index) {
        return numOfTwo().multiply(ema.getValue(index)).subtract(emaOfEma.getValue(index));
    }
}
