package trade.invision.indicators.indicators.numdatapoint;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.num.NumDatapoint;
import trade.invision.indicators.series.num.NumSeries;

import java.time.Instant;

/**
 * {@link NumDatapointInstant} is an {@link Instant} {@link Indicator} to provide {@link NumDatapoint#getInstant()}.
 */
public class NumDatapointInstant extends CachelessIndicator<Instant> {

    /**
     * Gets a {@link NumDatapointInstant}.
     *
     * @param numSeries the {@link NumSeries}
     */
    public static NumDatapointInstant instantOf(NumSeries numSeries) {
        return new NumDatapointInstant(numSeries);
    }

    private final NumSeries numSeries;

    protected NumDatapointInstant(NumSeries numSeries) {
        super(numSeries, 0);
        this.numSeries = numSeries;
    }

    @Override
    protected Instant calculate(long index) {
        return numSeries.get(index).getInstant();
    }
}
