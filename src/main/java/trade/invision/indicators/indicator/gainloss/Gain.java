package trade.invision.indicators.indicator.gainloss;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.previous.PreviousDifference;
import trade.invision.num.Num;

/**
 * {@link Gain} is a {@link Num} {@link Indicator} to provide the gain of the current value compared to the
 * <i>n</i>-th previous value. This will always return a positive number or zero.
 */
public class Gain extends PreviousDifference {

    /**
     * Calls {@link #gain(Indicator, int)} with <code>n</code> set to <code>1</code>.
     */
    public static Gain gain(Indicator<Num> indicator) {
        return gain(indicator, 1);
    }

    /**
     * Gets a {@link Gain}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static Gain gain(Indicator<Num> indicator, int n) {
        return CACHE.get(new CacheKey(indicator, n), key -> new Gain(indicator, n));
    }

    private static final Cache<CacheKey, Gain> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int n;
    }

    protected Gain(Indicator<Num> indicator, int n) {
        super(indicator, n);
    }

    @Override
    protected Num calculate(long index) {
        return numOfZero().maximum(super.calculate(index));
    }
}
