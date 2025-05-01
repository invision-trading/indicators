package trade.invision.indicators.indicator.meta.indicator;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.CachingIndicator;
import trade.invision.indicators.indicator.Indicator;

/**
 * {@link IsCaching} is a {@link Boolean} {@link Indicator} to check if the given {@link Indicator}
 * {@link #isCaching()}.
 */
public class IsCaching extends CachingIndicator<Boolean> { // Cache so same 'index' yields same result

    /**
     * Gets a {@link IsCaching}.
     *
     * @param indicator the {@link Indicator}
     */
    public static IsCaching isCaching(Indicator<?> indicator) {
        return CACHE.get(new CacheKey(indicator), key -> new IsCaching(indicator));
    }

    private static final Cache<CacheKey, IsCaching> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<?> indicator;
    }

    private final Indicator<?> indicator;

    protected IsCaching(Indicator<?> indicator) {
        super(indicator.getSeries(), 0);
        this.indicator = indicator;
    }

    @Override
    protected Boolean calculate(long index) {
        return indicator.isCaching();
    }
}
