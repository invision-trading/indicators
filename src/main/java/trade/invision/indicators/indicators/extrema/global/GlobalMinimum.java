package trade.invision.indicators.indicators.extrema.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link GlobalMinimum} is a {@link Num} {@link Indicator} to provide the global minimum extrema (all-time lowest
 * value).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Maximum_and_minimum">Wikipedia</a>
 */
public class GlobalMinimum extends AbstractGlobalExtrema {

    /**
     * Gets a {@link GlobalMinimum}.
     *
     * @param indicator the {@link Indicator}
     */
    public static GlobalMinimum globalMinimum(Indicator<Num> indicator) {
        return CACHE.get(new CacheKey(indicator), key -> new GlobalMinimum(indicator));
    }

    private static final Cache<CacheKey, GlobalMinimum> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
    }

    protected GlobalMinimum(Indicator<Num> indicator) {
        super(indicator, false);
    }
}
