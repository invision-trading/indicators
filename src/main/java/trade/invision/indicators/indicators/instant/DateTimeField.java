package trade.invision.indicators.indicators.instant;

import trade.invision.indicators.indicators.Indicator;
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
     * Convenience static method for {@link #DateTimeField(Indicator, ChronoField)}.
     */
    public static DateTimeField dateTimeField(Indicator<Instant> indicator, ChronoField field) {
        return new DateTimeField(indicator, field);
    }

    /**
     * Convenience static method for {@link #DateTimeField(Indicator, ChronoField, ZoneId)}.
     */
    public static DateTimeField dateTimeField(Indicator<Instant> indicator, ChronoField field, ZoneId zoneId) {
        return new DateTimeField(indicator, field, zoneId);
    }

    private final Indicator<Instant> indicator;
    private final ChronoField field;
    private final ZoneId zoneId;

    /**
     * Calls {@link #DateTimeField(Indicator, ChronoField, ZoneId)} with <code>zoneId</code> set to
     * {@link ZoneOffset#UTC}.
     */
    public DateTimeField(Indicator<Instant> indicator, ChronoField field) {
        this(indicator, field, UTC);
    }

    /**
     * Instantiates a new {@link DateTimeField}.
     *
     * @param indicator the {@link Indicator}
     * @param field     the {@link ChronoField}
     * @param zoneId    the {@link ZoneId} to perform the operation in
     */
    public DateTimeField(Indicator<Instant> indicator, ChronoField field, ZoneId zoneId) {
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
