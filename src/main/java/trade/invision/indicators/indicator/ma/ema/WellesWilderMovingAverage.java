package trade.invision.indicators.indicator.ma.ema;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

/**
 * {@link WellesWilderMovingAverage} is a {@link Num} {@link Indicator} to provide a Welles Wilder Moving Average (WWMA)
 * over a <code>length</code> of values. This is the same as an Exponential Moving Average (EMA) except it uses a
 * <code>multiplier</code> of <code>1 / length</code>. Used in Welles Wilder indicators, like the Relative Strength
 * Index (RSI).
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=WellesMA.htm">FM Labs</a>
 */
public class WellesWilderMovingAverage extends AbstractExponentialMovingAverage {

    /**
     * @see #wellesWilderMovingAverage(Indicator, int)
     */
    public static WellesWilderMovingAverage wwma(Indicator<Num> indicator, int length) {
        return wellesWilderMovingAverage(indicator, length);
    }

    /**
     * Gets a {@link WellesWilderMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static WellesWilderMovingAverage wellesWilderMovingAverage(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new WellesWilderMovingAverage(indicator, length));
    }

    private static final Cache<CacheKey, WellesWilderMovingAverage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    protected WellesWilderMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator, length, indicator.getSeries().getNumFactory().of(length).reciprocal());
    }
}
