package trade.invision.indicators.indicators.volume;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.previous.PreviousDifference;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicators.bar.Volume.volume;

/**
 * {@link VolumeDifference} is a {@link Num} {@link Indicator} to use {@link PreviousDifference} with {@link Volume}.
 */
public class VolumeDifference extends PreviousDifference {

    /**
     * Calls {@link #volumePriceDifference(BarSeries, int)} with <code>n</code> set to <code>1</code>.
     */
    public static VolumeDifference volumePriceDifference(BarSeries barSeries) {
        return volumePriceDifference(barSeries, 1);
    }

    /**
     * Gets a {@link VolumeDifference}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static VolumeDifference volumePriceDifference(BarSeries barSeries, int n) {
        return CACHE.get(new CacheKey(barSeries, n), key -> new VolumeDifference(barSeries, n));
    }

    private static final Cache<CacheKey, VolumeDifference> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int n;
    }

    protected VolumeDifference(BarSeries barSeries, int n) {
        super(volume(barSeries), n);
    }
}
