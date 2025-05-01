package trade.invision.indicators.indicator.rvol;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.bar.Volume;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicator.bar.Volume.volume;

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
     * Gets a {@link RelativeVolume}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static RelativeVolume relativeVolume(BarSeries barSeries, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(barSeries, length, movingAverageSupplier),
                key -> new RelativeVolume(barSeries, length, movingAverageSupplier));
    }

    private static final Cache<CacheKey, RelativeVolume> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int length;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final Volume volume;
    private final Indicator<Num> averageVolume;

    protected RelativeVolume(BarSeries barSeries, int length, MovingAverageSupplier movingAverageSupplier) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        volume = volume(barSeries);
        averageVolume = movingAverageSupplier.supply(volume, length);
    }

    @Override
    protected Num calculate(long index) {
        return volume.getValue(index).divide(averageVolume.getValue(index));
    }
}
