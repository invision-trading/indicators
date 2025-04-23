package trade.invision.indicators.indicators.rvol;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link RelativeVolume} is a {@link Num} {@link Indicator} to provide the Relative Volume (RVOL) over a
 * <code>length</code> of {@link Bar}s.
 *
 * @see <a
 * href="https://chartschool.stockcharts.com/table-of-contents/technical-indicators-and-overlays/technical-indicators/relative-volume-rvol">StockCharts</a>
 */
public class RelativeVolume extends Indicator<Num> {

    /**
     * @see #relativeVolume(BarSeries, int, MovingAverageSupplier)
     */
    public static RelativeVolume rvol(BarSeries barSeries, int length, MovingAverageSupplier movingAverageSupplier) {
        return relativeVolume(barSeries, length, movingAverageSupplier);
    }

    /**
     * Convenience static method for {@link #RelativeVolume(BarSeries, int, MovingAverageSupplier)}.
     */
    public static RelativeVolume relativeVolume(BarSeries barSeries, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return new RelativeVolume(barSeries, length, movingAverageSupplier);
    }

    private final Volume volume;
    private final Indicator<Num> averageVolume;

    /**
     * Instantiates a new {@link RelativeVolume}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public RelativeVolume(BarSeries barSeries, int length, MovingAverageSupplier movingAverageSupplier) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        volume = new Volume(barSeries);
        averageVolume = movingAverageSupplier.supply(volume, length);
    }

    @Override
    protected Num calculate(long index) {
        return volume.getValue(index).divide(averageVolume.getValue(index));
    }
}
