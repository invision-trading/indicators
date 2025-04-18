package trade.invision.indicators.indicators.volume;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.previous.PreviousDifference;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link VolumeDifference} is a {@link Num} {@link Indicator} to use {@link PreviousDifference} with {@link Volume}.
 */
public class VolumeDifference extends PreviousDifference {

    /**
     * Convenience static method for {@link #VolumeDifference(BarSeries)}.
     */
    public static VolumeDifference volumeDifference(BarSeries barSeries) {
        return new VolumeDifference(barSeries);
    }

    /**
     * Convenience static method for {@link #VolumeDifference(BarSeries, int)}.
     */
    public static VolumeDifference volumeDifference(BarSeries barSeries, int length) {
        return new VolumeDifference(barSeries, length);
    }

    /**
     * Instantiates a new {@link VolumeDifference} with <code>n</code> set to <code>1</code>.
     *
     * @param barSeries the {@link BarSeries}
     */
    public VolumeDifference(BarSeries barSeries) {
        this(barSeries, 1);
    }

    /**
     * Instantiates a new {@link VolumeDifference}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public VolumeDifference(BarSeries barSeries, int n) {
        super(new Volume(barSeries), n);
    }
}
