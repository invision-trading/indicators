package trade.invision.indicators.indicators.instant;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.CachingIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;

import java.time.Instant;

import static java.time.Instant.now;

/**
 * {@link CurrentRealTime} is an {@link Instant} {@link Indicator} to provide the current real time.
 */
public class CurrentRealTime extends CachingIndicator<Instant> { // Cache so same 'index' yields same result

    /**
     * Gets a {@link CurrentRealTime}.
     *
     * @param series the {@link #getSeries()}
     */
    public static CurrentRealTime currentRealTime(Series<?> series) {
        return CACHE.get(new CacheKey(series), key -> new CurrentRealTime(series));
    }

    private static final Cache<CacheKey, CurrentRealTime> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Series<?> series;
    }

    protected CurrentRealTime(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Instant calculate(long index) {
        return now();
    }
}
