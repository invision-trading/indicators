package trade.invision.indicators.indicator.ma.sma;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.cumulative.CumulativeSum;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.min;
import static trade.invision.indicators.indicator.cumulative.CumulativeSum.cumulativeSum;

/**
 * {@link SimpleMovingAverage} is a {@link Num} {@link Indicator} to provide a Simple Moving Average (SMA) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/s/sma.asp">Investopedia</a>
 */
public class SimpleMovingAverage extends Indicator<Num> {

    /**
     * @see #simpleMovingAverage(Indicator, int)
     */
    public static SimpleMovingAverage sma(Indicator<Num> indicator, int length) {
        return simpleMovingAverage(indicator, length);
    }

    /**
     * Gets a {@link SimpleMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static SimpleMovingAverage simpleMovingAverage(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new SimpleMovingAverage(indicator, length));
    }

    private static final Cache<CacheKey, SimpleMovingAverage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final int length;
    private final CumulativeSum sum;

    protected SimpleMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.length = length;
        sum = cumulativeSum(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        return sum.getValue(index).divide(min(index + 1, length));
    }
}
