package trade.invision.indicators.indicator.instant;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

import static java.time.ZoneOffset.UTC;

/**
 * {@link DateTimeField} is a {@link Num} {@link Indicator} to provide the {@link ChronoField} of an {@link Instant}.
 */
public class DateTimeField extends Indicator<Num> {

    /**
     * Calls {@link #dateTimeField(Indicator, ChronoField, ZoneId)} with <code>zoneId</code> set to
     * {@link ZoneOffset#UTC}.
     */
    public static DateTimeField dateTimeField(Indicator<Instant> indicator, ChronoField field) {
        return dateTimeField(indicator, field, UTC);
    }

    /**
     * Gets a {@link DateTimeField}.
     *
     * @param indicator the {@link Indicator}
     * @param field     the {@link ChronoField}
     * @param zoneId    the {@link ZoneId} to perform the operation in
     */
    public static DateTimeField dateTimeField(Indicator<Instant> indicator, ChronoField field, ZoneId zoneId) {
        return CACHE.get(new CacheKey(indicator, field, zoneId), key -> new DateTimeField(indicator, field, zoneId));
    }

    private static final Cache<CacheKey, DateTimeField> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Instant> indicator;
        ChronoField field;
        ZoneId zoneId;
    }

    private final Indicator<Instant> indicator;
    private final ChronoField field;
    private final ZoneId zoneId;

    protected DateTimeField(Indicator<Instant> indicator, ChronoField field, ZoneId zoneId) {
        super(indicator.getSeries(), 0);
        this.indicator = indicator;
        this.field = field;
        this.zoneId = zoneId;
    }

    @Override
    protected Num calculate(long index) {
        return numOf(indicator.getValue(index).atZone(zoneId).get(field));
    }
}
