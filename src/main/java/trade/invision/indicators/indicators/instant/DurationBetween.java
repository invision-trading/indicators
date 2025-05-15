package trade.invision.indicators.indicators.instant;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static java.time.ZoneOffset.UTC;

/**
 * {@link DurationBetween} is a {@link Num} {@link Indicator} to provide the {@link Duration} between two
 * {@link Instant}s in the given {@link ChronoUnit}. A positive value will be returned if <code>second</code> is after
 * <code>first</code>. A negative value will be returned if <code>second</code> is before <code>first</code>.
 */
public class DurationBetween extends Indicator<Num> {

    /**
     * Gets a {@link DurationBetween}.
     *
     * @param first  the first {@link Indicator}
     * @param second the second {@link Indicator}
     * @param unit   the {@link ChronoUnit}
     */
    public static DurationBetween durationBetween(Indicator<Instant> first, Indicator<Instant> second,
            ChronoUnit unit) {
        return CACHE.get(new CacheKey(first, second, unit), key -> new DurationBetween(first, second, unit));
    }

    private static final Cache<CacheKey, DurationBetween> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Instant> first;
        Indicator<Instant> second;
        ChronoUnit unit;
    }

    private final Indicator<Instant> first;
    private final Indicator<Instant> second;
    private final ChronoUnit unit;

    protected DurationBetween(Indicator<Instant> first, Indicator<Instant> second, ChronoUnit unit) {
        super(first.getSeries(), 0);
        this.first = first;
        this.second = second;
        this.unit = unit;
    }

    @Override
    protected Num calculate(long index) {
        return numOf(unit.between(first.getValue(index).atZone(UTC), second.getValue(index).atZone(UTC)));
    }
}
