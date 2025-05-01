package trade.invision.indicators.indicator.extrema.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

/**
 * {@link GlobalMaximum} is a {@link Num} {@link Indicator} to provide the global maximum extrema (all-time highest
 * value).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Maximum_and_minimum">Wikipedia</a>
 */
public class GlobalMaximum extends AbstractGlobalExtrema {

    /**
     * Gets a {@link GlobalMaximum}.
     *
     * @param indicator the {@link Indicator}
     */
    public static GlobalMaximum globalMaximum(Indicator<Num> indicator) {
        return CACHE.get(new CacheKey(indicator), key -> new GlobalMaximum(indicator));
    }

    private static final Cache<CacheKey, GlobalMaximum> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
    }

    protected GlobalMaximum(Indicator<Num> indicator) {
        super(indicator, true);
    }
}
