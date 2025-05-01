package trade.invision.indicators.indicator.bar;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link TradeSize} is a {@link Num} {@link Indicator} to provide the average trade size within a {@link Bar}. The
 * formula used is: <code>volume / tradeCount</code>.
 *
 * @see <a href="https://www.investopedia.com/articles/investing/082614/how-stock-market-works.asp">Investopedia</a>
 */
public class TradeSize extends Indicator<Num> {

    /**
     * Gets a {@link TradeSize}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static TradeSize tradeSize(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new TradeSize(barSeries));
    }

    private static final Cache<CacheKey, TradeSize> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final BarSeries barSeries;

    protected TradeSize(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return bar.getVolume().divide(numOf(bar.getTradeCount())).ifNaN(numOfZero());
    }
}
