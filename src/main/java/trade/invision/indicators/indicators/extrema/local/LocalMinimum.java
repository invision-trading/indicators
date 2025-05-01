package trade.invision.indicators.indicators.extrema.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link LocalMinimum} is a {@link Num} {@link Indicator} to provide the local minimum extrema (lowest value) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Maximum_and_minimum">Wikipedia</a>
 */
public class LocalMinimum extends AbstractLocalExtrema {

    /**
     * Gets a {@link LocalMinimum}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static LocalMinimum localMinimum(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new LocalMinimum(indicator, length));
    }

    private static final Cache<CacheKey, LocalMinimum> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    protected LocalMinimum(Indicator<Num> indicator, int length) {
        super(indicator, length, false);
    }
}
