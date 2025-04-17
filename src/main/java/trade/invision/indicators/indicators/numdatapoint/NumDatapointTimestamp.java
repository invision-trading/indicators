package trade.invision.indicators.indicators.numdatapoint;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.num.NumDatapoint;
import trade.invision.indicators.series.num.NumSeries;

import java.time.Instant;

/**
 * {@link NumDatapointTimestamp} is an {@link Instant} {@link Indicator} to provide
 * {@link NumDatapoint#getTimestamp()}.
 */
public class NumDatapointTimestamp extends CachelessIndicator<Instant> {

    /**
     * Convenience static method for {@link #NumDatapointTimestamp(NumSeries)}.
     */
    public static NumDatapointTimestamp timestampOf(NumSeries numSeries) {
        return new NumDatapointTimestamp(numSeries);
    }

    private final NumSeries numSeries;

    /**
     * Instantiates a new {@link NumDatapointTimestamp}.
     *
     * @param numSeries the {@link NumSeries}
     */
    public NumDatapointTimestamp(NumSeries numSeries) {
        super(numSeries, 0);
        this.numSeries = numSeries;
    }

    @Override
    protected Instant calculate(long index) {
        return numSeries.get(index).getTimestamp();
    }
}
