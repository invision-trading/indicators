package trade.invision.indicators.indicators.volume;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.previous.PreviousPrecentChange;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link VolumePercentChange} is a {@link Num} {@link Indicator} to use {@link PreviousPrecentChange} with
 * {@link Volume}. This is also referred to as Rate of Change (ROC) or Momentum. The percentage is represented as a
 * fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 */
public class VolumePercentChange extends PreviousPrecentChange {

    /**
     * Convenience static method for {@link #VolumePercentChange(BarSeries)}.
     */
    public static VolumePercentChange volumePercentChange(BarSeries barSeries) {
        return new VolumePercentChange(barSeries);
    }

    /**
     * Convenience static method for {@link #VolumePercentChange(BarSeries, int)}.
     */
    public static VolumePercentChange volumePercentChange(BarSeries barSeries, int length) {
        return new VolumePercentChange(barSeries, length);
    }

    /**
     * Instantiates a new {@link VolumePercentChange} with <code>n</code> set to <code>1</code>.
     *
     * @param barSeries the {@link BarSeries}
     */
    public VolumePercentChange(BarSeries barSeries) {
        this(barSeries, 1);
    }

    /**
     * Instantiates a new {@link VolumePercentChange}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public VolumePercentChange(BarSeries barSeries, int n) {
        super(new Volume(barSeries), n);
    }
}
