package trade.invision.indicators.indicators.instant;

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
     * Convenience static method for {@link #WithDateTimeField(Indicator, Indicator, ChronoField)}.
     */
    public static WithDateTimeField withDateTimeField(Indicator<Instant> base, Indicator<Instant> reference,
            ChronoField field) {
        return new WithDateTimeField(base, reference, field);
    }

    /**
     * Convenience static method for {@link #WithDateTimeField(Indicator, Indicator, ChronoField, ZoneId)}.
     */
    public static WithDateTimeField withDateTimeField(Indicator<Instant> base, Indicator<Instant> reference,
            ChronoField field, ZoneId zoneId) {
        return new WithDateTimeField(base, reference, field, zoneId);
    }

    private final Indicator<Instant> base;
    private final Indicator<Instant> reference;
    private final ChronoField field;
    private final ZoneId zoneId;

    /**
     * Calls {@link #WithDateTimeField(Indicator, Indicator, ChronoField, ZoneId)} with <code>zoneId</code> set to
     * {@link ZoneOffset#UTC}.
     */
    public WithDateTimeField(Indicator<Instant> base, Indicator<Instant> reference, ChronoField field) {
        this(base, reference, field, UTC);
    }

    /**
     * Instantiates a new {@link WithDateTimeField}.
     *
     * @param base      the base {@link Indicator}
     * @param reference the reference {@link Indicator}
     * @param field     the {@link ChronoField}
     * @param zoneId    the {@link ZoneId} to perform the operation in
     */
    public WithDateTimeField(Indicator<Instant> base, Indicator<Instant> reference, ChronoField field, ZoneId zoneId) {
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
