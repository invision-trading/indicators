package trade.invision.indicators.indicator.tr;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link TrueRange} is a {@link Num} {@link Indicator} to provide the True Range (TR) of a {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/a/atr.asp">Investopedia</a>
 */
public class TrueRange extends Indicator<Num> {

    /**
     * @see #trueRange(BarSeries)
     */
    public static TrueRange tr(BarSeries barSeries) {
        return trueRange(barSeries);
    }

    /**
     * Gets a {@link TrueRange}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static TrueRange trueRange(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new TrueRange(barSeries));
    }

    private static final Cache<CacheKey, TrueRange> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final BarSeries barSeries;

    protected TrueRange(BarSeries barSeries) {
        super(barSeries, 1);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar currentBar = barSeries.get(index);
        final Num hl = currentBar.getHigh().subtract(currentBar.getLow()).absoluteValue();
        if (index == 0) {
            return hl;
        }
        final Bar previousBar = barSeries.get(index - 1);
        final Num hc = currentBar.getHigh().subtract(previousBar.getClose()).absoluteValue();
        final Num lc = currentBar.getLow().subtract(previousBar.getClose()).absoluteValue();
        return hl.maximum(hc.maximum(lc));
    }
}
