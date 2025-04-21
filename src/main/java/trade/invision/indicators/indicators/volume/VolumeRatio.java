package trade.invision.indicators.indicators.volume;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.previous.PreviousRatio;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link VolumeRatio} is a {@link Num} {@link Indicator} to use {@link PreviousRatio} with {@link Volume}.
 */
public class VolumeRatio extends PreviousRatio {

    /**
     * Convenience static method for {@link #VolumeRatio(BarSeries)}.
     */
    public static VolumeRatio volumeRatio(BarSeries barSeries) {
        return new VolumeRatio(barSeries);
    }

    /**
     * Convenience static method for {@link #VolumeRatio(BarSeries, int)}.
     */
    public static VolumeRatio volumeRatio(BarSeries barSeries, int n) {
        return new VolumeRatio(barSeries, n);
    }

    /**
     * Instantiates a new {@link VolumeRatio} with <code>n</code> set to <code>1</code>.
     *
     * @param barSeries the {@link BarSeries}
     */
    public VolumeRatio(BarSeries barSeries) {
        this(barSeries, 1);
    }

    /**
     * Instantiates a new {@link VolumeRatio}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public VolumeRatio(BarSeries barSeries, int n) {
        super(new Volume(barSeries), n);
    }
}
