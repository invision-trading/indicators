package trade.invision.indicators.indicator.numdatapoint;

import trade.invision.indicators.indicator.CachelessIndicator;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.num.NumDatapoint;
import trade.invision.indicators.series.num.NumSeries;
import trade.invision.num.Num;

/**
 * {@link NumDatapointValue} is a {@link Num} {@link Indicator} to provide {@link NumDatapoint#getValue()}.
 */
public class NumDatapointValue extends CachelessIndicator<Num> {

    /**
     * Gets a {@link NumDatapointValue}.
     *
     * @param numSeries the {@link NumSeries}
     */
    public static NumDatapointValue valueOf(NumSeries numSeries) {
        return new NumDatapointValue(numSeries);
    }

    private final NumSeries numSeries;

    protected NumDatapointValue(NumSeries numSeries) {
        super(numSeries, 0);
        this.numSeries = numSeries;
    }

    @Override
    protected Num calculate(long index) {
        return numSeries.get(index).getValue();
    }
}
