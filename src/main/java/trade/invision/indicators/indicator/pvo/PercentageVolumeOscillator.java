package trade.invision.indicators.indicator.pvo;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.bar.Volume;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.indicators.indicator.ppo.PercentagePriceOscillator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.bar.Volume.volume;

/**
 * {@link PercentageVolumeOscillator} is a {@link Num} {@link Indicator} to provide the Percentage Volume Oscillator
 * (PVO) over a <code>shortLength</code> and <code>longLength</code> of {@link Bar}s. This uses the
 * {@link PercentagePriceOscillator} with the {@link Volume} {@link Indicator}.
 *
 * @see <a
 * href="https://chartschool.stockcharts.com/table-of-contents/technical-indicators-and-overlays/technical-indicators/percentage-volume-oscillator-pvo">StockCharts</a>
 */
public class PercentageVolumeOscillator extends PercentagePriceOscillator {

    /**
     * @see #percentageVolumeOscillator(BarSeries, int, int, MovingAverageSupplier)
     */
    public static PercentageVolumeOscillator pvo(BarSeries barSeries, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        return percentageVolumeOscillator(barSeries, shortLength, longLength, movingAverageSupplier);
    }

    /**
     * Gets a {@link PercentageVolumeOscillator}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param shortLength           the short averaging length (typically 12)
     * @param longLength            the long averaging length (typically 26)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static PercentageVolumeOscillator percentageVolumeOscillator(BarSeries barSeries,
            int shortLength, int longLength, MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(barSeries, shortLength, longLength, movingAverageSupplier),
                key -> new PercentageVolumeOscillator(barSeries, shortLength, longLength, movingAverageSupplier));
    }

    private static final Cache<CacheKey, PercentageVolumeOscillator> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int shortLength;
        int longLength;
        MovingAverageSupplier movingAverageSupplier;
    }

    protected PercentageVolumeOscillator(BarSeries barSeries, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        super(volume(barSeries), shortLength, longLength, movingAverageSupplier);
    }
}
