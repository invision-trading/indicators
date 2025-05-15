package trade.invision.indicators.indicators.random;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.CachingIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;
import trade.invision.num.NumFactory;

/**
 * {@link RandomNum} is a {@link Num} {@link Indicator} to provide {@link NumFactory#random()} for each
 * <code>index</code>.
 */
public class RandomNum extends CachingIndicator<Num> { // Cache so same 'index' yields same result

    /**
     * Gets a {@link RandomNum}.
     *
     * @param series the {@link #getSeries()}
     */
    public static RandomNum randomNum(Series<?> series) {
        return CACHE.get(new CacheKey(series), key -> new RandomNum(series));
    }

    private static final Cache<CacheKey, RandomNum> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Series<?> series;
    }

    protected RandomNum(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Num calculate(long index) {
        return series.getNumFactory().random();
    }
}
