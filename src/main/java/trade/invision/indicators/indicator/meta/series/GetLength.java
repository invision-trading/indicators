package trade.invision.indicators.indicator.meta.series;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.CachingIndicator;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link GetLength} is a {@link Num} {@link Indicator} to provide the {@link Series#getLength()} of the given
 * {@link Series}.
 */
public class GetLength extends CachingIndicator<Num> { // Cache so same 'index' yields same result

    /**
     * Gets a {@link GetLength}.
     *
     * @param series the {@link #getSeries()}
     */
    public static GetLength getLength(Series<?> series) {
        return CACHE.get(new CacheKey(series), key -> new GetLength(series));
    }

    private static final Cache<CacheKey, GetLength> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Series<?> series;
    }

    protected GetLength(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Num calculate(long index) {
        return numOf(series.getLength());
    }
}
