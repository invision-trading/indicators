package trade.invision.indicators.indicator.volume;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.bar.Volume;
import trade.invision.indicators.indicator.previous.PreviousPercentChange;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.bar.Volume.volume;

/**
 * {@link VolumePercentChange} is a {@link Num} {@link Indicator} to use {@link PreviousPercentChange} with
 * {@link Volume}. This is also referred to as Rate of Change (ROC) or Momentum. The percentage is represented as a
 * fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 */
public class VolumePercentChange extends PreviousPercentChange {

    /**
     * Calls {@link #volumePricePercentChange(BarSeries, int)} with <code>n</code> set to <code>1</code>.
     */
    public static VolumePercentChange volumePricePercentChange(BarSeries barSeries) {
        return volumePricePercentChange(barSeries, 1);
    }

    /**
     * Gets a {@link VolumePercentChange}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static VolumePercentChange volumePricePercentChange(BarSeries barSeries, int n) {
        return CACHE.get(new CacheKey(barSeries, n), key -> new VolumePercentChange(barSeries, n));
    }

    private static final Cache<CacheKey, VolumePercentChange> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int n;
    }

    protected VolumePercentChange(BarSeries barSeries, int n) {
        super(volume(barSeries), n);
    }
}
