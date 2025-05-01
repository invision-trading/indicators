package trade.invision.indicators.indicator.bar;

import trade.invision.indicators.indicator.CachelessIndicator;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link TradeCount} is a {@link Num} {@link Indicator} to provide {@link Bar#getTradeCount()}.
 *
 * @see <a href="https://www.investopedia.com/articles/investing/082614/how-stock-market-works.asp">Investopedia</a>
 */
public class TradeCount extends CachelessIndicator<Num> {

    /**
     * Gets a {@link TradeCount}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static TradeCount tradeCount(BarSeries barSeries) {
        return new TradeCount(barSeries);
    }

    private final BarSeries barSeries;

    protected TradeCount(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        return barSeries.get(index).getTradeCount();
    }
}
