package trade.invision.indicators.indicator.crossed;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

/**
 * {@link CrossedDown} is a {@link Boolean} {@link Indicator} to provide a negative crossing signal. A negative crossing
 * occurs when <code>first</code> crosses below <code>second</code>.
 */
public class CrossedDown extends CrossedUp {

    /**
     * Gets a {@link CrossedDown}.
     *
     * @param first  the first {@link Indicator}
     * @param second the second {@link Indicator}
     */
    public static CrossedDown crossedDown(Indicator<Num> first, Indicator<Num> second) {
        return CACHE.get(new CacheKey(first, second), key -> new CrossedDown(first, second));
    }

    private static final Cache<CacheKey, CrossedDown> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> first;
        Indicator<Num> second;
    }

    protected CrossedDown(Indicator<Num> first, Indicator<Num> second) {
        super(second, first);
    }
}
