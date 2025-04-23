package trade.invision.indicators.indicators.chaikin;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.clv.CloseLocationValue;
import trade.invision.indicators.indicators.cumulative.CumulativeSum;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link ChaikinMoneyFlow} is a {@link Num} {@link Indicator} to provide the Chaikin Money Flow (CMF) over a
 * <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=ChaikinMoneyFlow.htm">FM Labs</a>
 */
public class ChaikinMoneyFlow extends Indicator<Num> {

    /**
     * @see #chaikinMoneyFlow(BarSeries, int)
     */
    public static ChaikinMoneyFlow cmf(BarSeries barSeries, int length) {
        return chaikinMoneyFlow(barSeries, length);
    }

    /**
     * Convenience static method for {@link #ChaikinMoneyFlow(BarSeries, int)}.
     */
    public static ChaikinMoneyFlow chaikinMoneyFlow(BarSeries barSeries, int length) {
        return new ChaikinMoneyFlow(barSeries, length);
    }

    private final CumulativeSum cumulativeNumerator;
    private final CumulativeSum cumulativeVolume;

    /**
     * Instantiates a new {@link ChaikinMoneyFlow}.
     *
     * @param barSeries the {@link BarSeries}
     * @param length    the number of values to look back at
     */
    public ChaikinMoneyFlow(BarSeries barSeries, int length) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        final Volume volume = new Volume(barSeries);
        final CloseLocationValue clv = new CloseLocationValue(barSeries);
        cumulativeNumerator = new CumulativeSum(new Indicator<>(series, 0) {
            @Override
            protected Num calculate(long index) {
                return clv.getValue(index).multiply(volume.getValue(index));
            }
        }, length);
        cumulativeVolume = new CumulativeSum(volume, length);
    }

    @Override
    protected Num calculate(long index) {
        return cumulativeNumerator.getValue(index).divide(cumulativeVolume.getValue(index));
    }
}
