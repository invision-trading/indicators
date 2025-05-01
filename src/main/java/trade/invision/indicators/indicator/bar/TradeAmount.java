package trade.invision.indicators.indicator.bar;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.barprice.Ohlc4;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.bar.Volume.volume;
import static trade.invision.indicators.indicator.barprice.Ohlc4.ohlc4;

/**
 * {@link TradeAmount} is a {@link Num} {@link Indicator} to provide the approximate total traded amount of a
 * {@link Bar}. The formula used is: <code>OHLC/4 * volume</code>.
 *
 * @see <a href="https://www.investopedia.com/articles/investing/082614/how-stock-market-works.asp">Investopedia</a>
 */
public class TradeAmount extends Indicator<Num> {

    /**
     * Gets a {@link TradeAmount}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static TradeAmount tradeAmount(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new TradeAmount(barSeries));
    }

    private static final Cache<CacheKey, TradeAmount> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final Ohlc4 ohlc4;
    private final Volume volume;

    protected TradeAmount(BarSeries barSeries) {
        super(barSeries, 0);
        ohlc4 = ohlc4(barSeries);
        volume = volume(barSeries);
    }

    @Override
    protected Num calculate(long index) {
        return ohlc4.getValue(index).multiply(volume.getValue(index));
    }
}
