package trade.invision.indicators.indicator.instant;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.Instant.EPOCH;
import static java.time.ZoneOffset.UTC;

/**
 * {@link EpochOffset} is a {@link Num} {@link Indicator} to provide the Unix epoch offset of an {@link Instant} in a
 * given {@link ChronoUnit}. A positive value will be returned if the {@link Instant} is after the Unix epoch time. A
 * negative value will be returned if the {@link Instant} is before the Unix epoch time. The Unix epoch time is
 * 1970-01-01T00:00:00Z.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Unix_time">Wikipedia</a>
 */
public class EpochOffset extends Indicator<Num> {

    /**
     * Gets a {@link EpochOffset}.
     *
     * @param indicator the {@link Indicator}
     * @param unit      the {@link ChronoUnit}
     */
    public static EpochOffset epochOffset(Indicator<Instant> indicator, ChronoUnit unit) {
        return CACHE.get(new CacheKey(indicator, unit), key -> new EpochOffset(indicator, unit));
    }

    private static final Cache<CacheKey, EpochOffset> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Instant> indicator;
        ChronoUnit unit;
    }

    private static final ZonedDateTime EPOCH_AT_UTC = EPOCH.atZone(UTC);

    private final Indicator<Instant> indicator;
    private final ChronoUnit unit;

    protected EpochOffset(Indicator<Instant> indicator, ChronoUnit unit) {
        super(indicator.getSeries(), 0);
        this.indicator = indicator;
        this.unit = unit;
    }

    @Override
    protected Num calculate(long index) {
        return numOf(unit.between(EPOCH_AT_UTC, indicator.getValue(index).atZone(UTC)));
    }
}
