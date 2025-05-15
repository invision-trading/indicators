package trade.invision.indicators.indicators.random;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.CachingIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;

import java.util.concurrent.ThreadLocalRandom;

/**
 * {@link RandomBoolean} is a {@link Boolean} {@link Indicator} to provide a random {@link Boolean} for each
 * <code>index</code>.
 */
public class RandomBoolean extends CachingIndicator<Boolean> { // Cache so same 'index' yields same result

    /**
     * Gets a {@link RandomBoolean}.
     *
     * @param series the {@link #getSeries()}
     */
    public static RandomBoolean randomBoolean(Series<?> series) {
        return CACHE.get(new CacheKey(series), key -> new RandomBoolean(series));
    }

    private static final Cache<CacheKey, RandomBoolean> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Series<?> series;
    }

    protected RandomBoolean(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Boolean calculate(long index) {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
