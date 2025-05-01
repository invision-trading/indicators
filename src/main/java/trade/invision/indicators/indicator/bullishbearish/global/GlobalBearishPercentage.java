package trade.invision.indicators.indicator.bullishbearish.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link GlobalBearishPercentage} is a {@link Num} {@link Indicator} to provide the percentage of
 * {@link Bar#isBearish()} {@link Bar}s over all {@link Bar}s. The percentage is represented as a fractional. For
 * example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class GlobalBearishPercentage extends AbstractGlobalBullishBearishPercentage {

    /**
     * Gets a {@link GlobalBearishPercentage}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static GlobalBearishPercentage globalBearishPercentage(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new GlobalBearishPercentage(barSeries));
    }

    private static final Cache<CacheKey, GlobalBearishPercentage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    protected GlobalBearishPercentage(BarSeries barSeries) {
        super(barSeries, false);
    }
}
