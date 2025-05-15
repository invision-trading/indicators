package trade.invision.indicators.indicators.instant;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;

import java.time.Duration;
import java.time.Instant;

/**
 * {@link OffsetBy} is an {@link Instant} {@link Indicator} to provide an {@link Instant} that is offset by a given
 * {@link Duration}.
 */
public class OffsetBy extends Indicator<Instant> {

    /**
     * Gets a {@link OffsetBy}.
     *
     * @param indicator the {@link Indicator}
     * @param offset    the {@link Duration} offset
     */
    public static OffsetBy offsetBy(Indicator<Instant> indicator, Duration offset) {
        return CACHE.get(new CacheKey(indicator, offset), key -> new OffsetBy(indicator, offset));
    }

    private static final Cache<CacheKey, OffsetBy> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Instant> indicator;
        Duration offset;
    }

    private final Indicator<Instant> indicator;
    private final Duration offset;

    protected OffsetBy(Indicator<Instant> indicator, Duration offset) {
        super(indicator.getSeries(), 0);
        this.indicator = indicator;
        this.offset = offset;
    }

    @Override
    protected Instant calculate(long index) {
        return indicator.getValue(index).plus(offset);
    }
}
