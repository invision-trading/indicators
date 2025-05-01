package trade.invision.indicators.indicator.volume;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.bar.Volume;
import trade.invision.indicators.indicator.previous.PreviousRatio;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.bar.Volume.volume;

/**
 * {@link VolumeRatio} is a {@link Num} {@link Indicator} to use {@link PreviousRatio} with {@link Volume}.
 */
public class VolumeRatio extends PreviousRatio {

    /**
     * Calls {@link #volumePriceRatio(BarSeries, int)} with <code>n</code> set to <code>1</code>.
     */
    public static VolumeRatio volumePriceRatio(BarSeries barSeries) {
        return volumePriceRatio(barSeries, 1);
    }

    /**
     * Gets a {@link VolumeRatio}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static VolumeRatio volumePriceRatio(BarSeries barSeries, int n) {
        return CACHE.get(new CacheKey(barSeries, n), key -> new VolumeRatio(barSeries, n));
    }

    private static final Cache<CacheKey, VolumeRatio> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int n;
    }

    protected VolumeRatio(BarSeries barSeries, int n) {
        super(volume(barSeries), n);
    }
}
