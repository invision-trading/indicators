package trade.invision.indicators.indicators.bullishbearish.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link LocalBullishPercentage} is a {@link Num} {@link Indicator} to provide the percentage of
 * {@link Bar#isBullish()} {@link Bar}s over a <code>length</code> of {@link Bar}s. The percentage is represented as a
 * fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class LocalBullishPercentage extends AbstractLocalBullishBearishPercentage {

    /**
     * Gets a {@link LocalBullishPercentage}.
     *
     * @param barSeries the {@link BarSeries}
     * @param length    the number of values to look back at
     */
    public static LocalBullishPercentage localBullishPercentage(BarSeries barSeries, int length) {
        return CACHE.get(new CacheKey(barSeries, length), key -> new LocalBullishPercentage(barSeries, length));
    }

    private static final Cache<CacheKey, LocalBullishPercentage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int length;
    }

    protected LocalBullishPercentage(BarSeries barSeries, int length) {
        super(barSeries, length, true);
    }
}
