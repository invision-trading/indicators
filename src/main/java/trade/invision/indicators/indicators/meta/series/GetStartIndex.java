package trade.invision.indicators.indicators.meta.series;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.CachingIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link GetStartIndex} is a {@link Num} {@link Indicator} to provide the {@link Series#getStartIndex()} of the given
 * {@link Series}.
 */
public class GetStartIndex extends CachingIndicator<Num> { // Cache so same 'index' yields same result

    /**
     * Gets a {@link GetStartIndex}.
     *
     * @param series the {@link #getSeries()}
     */
    public static GetStartIndex getStartIndex(Series<?> series) {
        return CACHE.get(new CacheKey(series), key -> new GetStartIndex(series));
    }

    private static final Cache<CacheKey, GetStartIndex> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Series<?> series;
    }

    protected GetStartIndex(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Num calculate(long index) {
        return numOf(series.getStartIndex());
    }
}
