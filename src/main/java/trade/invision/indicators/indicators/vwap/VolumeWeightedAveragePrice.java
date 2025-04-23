package trade.invision.indicators.indicators.vwap;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.cumulative.CumulativeSum;
import trade.invision.indicators.indicators.mf.MoneyFlow;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link VolumeWeightedAveragePrice} is a {@link Num} {@link Indicator} to provide the Volume-Weighted Average Price
 * (VWAP) over a <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.investopedia.com/terms/v/vwap.asp">Investopedia</a>
 */
public class VolumeWeightedAveragePrice extends Indicator<Num> {

    /**
     * @see #volumeWeightedAveragePrice(BarSeries, int)
     */
    public static VolumeWeightedAveragePrice vwap(BarSeries barSeries, int length) {
        return volumeWeightedAveragePrice(barSeries, length);
    }

    /**
     * Convenience static method for {@link #VolumeWeightedAveragePrice(BarSeries, int)}.
     */
    public static VolumeWeightedAveragePrice volumeWeightedAveragePrice(BarSeries barSeries, int length) {
        return new VolumeWeightedAveragePrice(barSeries, length);
    }

    private final CumulativeSum cumulativeMoneyFlow;
    private final CumulativeSum cumulativeVolume;

    /**
     * Instantiates a new {@link VolumeWeightedAveragePrice}.
     *
     * @param barSeries the {@link BarSeries}
     * @param length    the number of values to look back at
     */
    public VolumeWeightedAveragePrice(BarSeries barSeries, int length) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        cumulativeMoneyFlow = new CumulativeSum(new MoneyFlow(barSeries), length);
        cumulativeVolume = new CumulativeSum(new Volume(barSeries), length);
    }

    @Override
    protected Num calculate(long index) {
        return cumulativeMoneyFlow.getValue(index).divide(cumulativeVolume.getValue(index));
    }
}
