package trade.invision.indicators.indicators.instant;

import trade.invision.indicators.indicators.Indicator;

import java.time.Duration;
import java.time.Instant;

/**
 * {@link OffsetBy} is an {@link Instant} {@link Indicator} to provide an {@link Instant} that is offset by a given
 * {@link Duration}.
 */
public class OffsetBy extends Indicator<Instant> {

    /**
     * Convenience static method for {@link #OffsetBy(Indicator, Duration)}.
     */
    public static OffsetBy offsetBy(Indicator<Instant> indicator, Duration offset) {
        return new OffsetBy(indicator, offset);
    }

    private final Indicator<Instant> indicator;
    private final Duration offset;

    /**
     * Instantiates a new {@link OffsetBy}.
     *
     * @param indicator the {@link Indicator}
     * @param offset    the {@link Duration} offset
     */
    public OffsetBy(Indicator<Instant> indicator, Duration offset) {
        super(indicator.getSeries(), 0);
        this.indicator = indicator;
        this.offset = offset;
    }

    @Override
    protected Instant calculate(long index) {
        return indicator.getValue(index).plus(offset);
    }
}
