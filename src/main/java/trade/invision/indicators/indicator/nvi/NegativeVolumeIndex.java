package trade.invision.indicators.indicator.nvi;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link NegativeVolumeIndex} is a {@link Num} {@link Indicator} to provide the Negative Volume Index (NVI). The
 * initial value is <code>100</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/n/nvi.asp">Investopedia</a>
 */
public class NegativeVolumeIndex extends AbstractPositiveNegativeVolumeIndex {

    /**
     * @see #negativeVolumeIndex(BarSeries)
     */
    public static NegativeVolumeIndex pvi(BarSeries barSeries) {
        return negativeVolumeIndex(barSeries);
    }

    /**
     * Gets a {@link NegativeVolumeIndex}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static NegativeVolumeIndex negativeVolumeIndex(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new NegativeVolumeIndex(barSeries));
    }

    private static final Cache<CacheKey, NegativeVolumeIndex> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    protected NegativeVolumeIndex(BarSeries barSeries) {
        super(barSeries, false);
    }
}
