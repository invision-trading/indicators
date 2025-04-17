package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.Indicator;
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
     * Convenience static method for {@link #TradeSize(BarSeries)}.
     */
    public static TradeSize tradeSize(BarSeries barSeries) {
        return new TradeSize(barSeries);
    }

    private final BarSeries barSeries;

    /**
     * Instantiates a new {@link TradeSize}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public TradeSize(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return bar.getVolume().divide(numOf(bar.getTradeCount())).ifNaN(numOfZero());
    }
}
