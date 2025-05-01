package trade.invision.indicators.indicators.nvi;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link PositiveVolumeIndex} is a {@link Num} {@link Indicator} to provide the Positive Volume Index (PVI). The
 * initial value is <code>100</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/p/pvi.asp">Investopedia</a>
 */
public class PositiveVolumeIndex extends AbstractPositiveNegativeVolumeIndex {

    /**
     * @see #positiveVolumeIndex(BarSeries)
     */
    public static PositiveVolumeIndex pvi(BarSeries barSeries) {
        return positiveVolumeIndex(barSeries);
    }

    /**
     * Gets a {@link PositiveVolumeIndex}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static PositiveVolumeIndex positiveVolumeIndex(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new PositiveVolumeIndex(barSeries));
    }

    private static final Cache<CacheKey, PositiveVolumeIndex> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    protected PositiveVolumeIndex(BarSeries barSeries) {
        super(barSeries, true);
    }
}
