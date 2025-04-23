package trade.invision.indicators.indicators.rvol;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.indicators.indicators.statistical.StandardDeviation;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link RelativeVolumeStandardDeviation} is a {@link Num} {@link Indicator} to provide the Relative Volume Standard
 * Deviation (RVOLStdDev) over a <code>length</code> of {@link Bar}s.
 *
 * @see <a
 * href="https://toslc.thinkorswim.com/center/reference/Tech-Indicators/studies-library/R-S/RelativeVolumeStDev">thinkorswim</a>
 */
public class RelativeVolumeStandardDeviation extends Indicator<Num> {

    /**
     * @see #relativeVolume(BarSeries, int, MovingAverageSupplier, boolean)
     */
    public static RelativeVolumeStandardDeviation rvolstddev(BarSeries barSeries, int length,
            MovingAverageSupplier movingAverageSupplier, boolean unbiased) {
        return relativeVolume(barSeries, length, movingAverageSupplier, unbiased);
    }

    /**
     * Convenience static method for
     * {@link #RelativeVolumeStandardDeviation(BarSeries, int, MovingAverageSupplier, boolean)}.
     */
    public static RelativeVolumeStandardDeviation relativeVolume(BarSeries barSeries, int length,
            MovingAverageSupplier movingAverageSupplier, boolean unbiased) {
        return new RelativeVolumeStandardDeviation(barSeries, length, movingAverageSupplier, unbiased);
    }

    private final Volume volume;
    private final Indicator<Num> averageVolume;
    private final StandardDeviation volumeStandardDeviation;

    /**
     * Instantiates a new {@link RelativeVolumeStandardDeviation}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     * @param unbiased              <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the
     *                              standard deviation calculation, <code>false</code> to use <code>n</code> (biased)
     */
    public RelativeVolumeStandardDeviation(BarSeries barSeries, int length, MovingAverageSupplier movingAverageSupplier,
            boolean unbiased) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        volume = new Volume(barSeries);
        averageVolume = movingAverageSupplier.supply(volume, length);
        volumeStandardDeviation = new StandardDeviation(volume, length, unbiased);
    }

    @Override
    protected Num calculate(long index) {
        return volume.getValue(index).subtract(averageVolume.getValue(index))
                .divide(volumeStandardDeviation.getValue(index));
    }
}
