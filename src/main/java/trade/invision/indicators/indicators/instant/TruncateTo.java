package trade.invision.indicators.indicators.instant;

import trade.invision.indicators.indicators.Indicator;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static java.time.ZoneOffset.UTC;

/**
 * {@link TruncateTo} is an {@link Instant} {@link Indicator} to provide truncated {@link Instant}s. Truncation returns
 * a copy of the original {@link Instant} with fields smaller than the given <code>unit</code> set to zero. For example,
 * truncating with the {@link ChronoUnit#MINUTES} unit will set the seconds and milli/micro/nanoseconds to zero.
 */
public class TruncateTo extends Indicator<Instant> {

    /**
     * Convenience static method for {@link #TruncateTo(Indicator, ChronoUnit)}.
     */
    public static TruncateTo truncateTo(Indicator<Instant> indicator, ChronoUnit unit) {
        return new TruncateTo(indicator, unit);
    }

    /**
     * Convenience static method for {@link #TruncateTo(Indicator, ChronoUnit, ZoneId)}.
     */
    public static TruncateTo truncateTo(Indicator<Instant> indicator, ChronoUnit unit, ZoneId zoneId) {
        return new TruncateTo(indicator, unit, zoneId);
    }

    private final Indicator<Instant> indicator;
    private final ChronoUnit unit;
    private final ZoneId zoneId;

    /**
     * Calls {@link #TruncateTo(Indicator, ChronoUnit, ZoneId)} with <code>zoneId</code> set to {@link ZoneOffset#UTC}.
     */
    public TruncateTo(Indicator<Instant> indicator, ChronoUnit unit) {
        this(indicator, unit, UTC);
    }

    /**
     * Instantiates a new {@link TruncateTo}.
     *
     * @param indicator the {@link Indicator}
     * @param unit      the {@link ChronoUnit}
     * @param zoneId    the {@link ZoneId} to perform the operation in
     */
    public TruncateTo(Indicator<Instant> indicator, ChronoUnit unit, ZoneId zoneId) {
        super(indicator.getSeries(), 0);
        this.indicator = indicator;
        this.unit = unit;
        this.zoneId = zoneId;
    }

    @Override
    protected Instant calculate(long index) {
        return indicator.getValue(index).atZone(zoneId).truncatedTo(unit).toInstant();
    }
}
