package trade.invision.indicators.indicators.instant;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

import static java.time.ZoneOffset.UTC;

/**
 * {@link WithDateTimeField} is an {@link Instant} {@link Indicator} to provide the <code>base</code> with the
 * {@link ChronoField} of the <code>reference</code>.
 */
public class WithDateTimeField extends Indicator<Instant> {

    /**
     * Calls {@link #withDateTimeField(Indicator, Indicator, ChronoField, ZoneId)} with <code>zoneId</code> set to
     * {@link ZoneOffset#UTC}.
     */
    public static WithDateTimeField withDateTimeField(Indicator<Instant> base, Indicator<Instant> reference,
            ChronoField field) {
        return withDateTimeField(base, reference, field, UTC);
    }

    /**
     * Gets a {@link WithDateTimeField}.
     *
     * @param base      the base {@link Indicator}
     * @param reference the reference {@link Indicator}
     * @param field     the {@link ChronoField}
     * @param zoneId    the {@link ZoneId} to perform the operation in
     */
    public static WithDateTimeField withDateTimeField(Indicator<Instant> base, Indicator<Instant> reference,
            ChronoField field, ZoneId zoneId) {
        return CACHE.get(new CacheKey(base, reference, field, zoneId),
                key -> new WithDateTimeField(base, reference, field, zoneId));
    }

    private static final Cache<CacheKey, WithDateTimeField> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Instant> base;
        Indicator<Instant> reference;
        ChronoField field;
        ZoneId zoneId;
    }

    private final Indicator<Instant> base;
    private final Indicator<Instant> reference;
    private final ChronoField field;
    private final ZoneId zoneId;

    protected WithDateTimeField(Indicator<Instant> base, Indicator<Instant> reference, ChronoField field,
            ZoneId zoneId) {
        super(base.getSeries(), 0);
        this.base = base;
        this.reference = reference;
        this.field = field;
        this.zoneId = zoneId;
    }

    @Override
    protected Instant calculate(long index) {
        return base.getValue(index).atZone(zoneId)
                .with(field, reference.getValue(index).atZone(zoneId).get(field)).toInstant();
    }
}
