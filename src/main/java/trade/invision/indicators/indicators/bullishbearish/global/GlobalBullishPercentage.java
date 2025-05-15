package trade.invision.indicators.indicators.bullishbearish.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link GlobalBullishPercentage} is a {@link Num} {@link Indicator} to provide the percentage of
 * {@link Bar#isBullish()} {@link Bar}s over all {@link Bar}s. The percentage is represented as a fractional. For
 * example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class GlobalBullishPercentage extends AbstractGlobalBullishBearishPercentage {

    /**
     * Gets a {@link GlobalBullishPercentage}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static GlobalBullishPercentage globalBullishPercentage(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new GlobalBullishPercentage(barSeries));
    }

    private static final Cache<CacheKey, GlobalBullishPercentage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    protected GlobalBullishPercentage(BarSeries barSeries) {
        super(barSeries, true);
    }
}
