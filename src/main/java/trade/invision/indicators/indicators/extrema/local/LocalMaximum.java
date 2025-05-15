package trade.invision.indicators.indicators.extrema.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link LocalMaximum} is a {@link Num} {@link Indicator} to provide the local maximum extrema (highest value) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Maximum_and_minimum">Wikipedia</a>
 */
public class LocalMaximum extends AbstractLocalExtrema {

    /**
     * Gets a {@link LocalMaximum}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static LocalMaximum localMaximum(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new LocalMaximum(indicator, length));
    }

    private static final Cache<CacheKey, LocalMaximum> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    protected LocalMaximum(Indicator<Num> indicator, int length) {
        super(indicator, length, true);
    }
}
