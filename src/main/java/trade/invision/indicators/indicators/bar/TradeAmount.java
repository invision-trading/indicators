package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.barprice.Ohlc4;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link TradeAmount} is a {@link Num} {@link Indicator} to provide the approximate total traded amount of a
 * {@link Bar}. The formula used is: <code>OHLC/4 * volume</code>.
 *
 * @see <a href="https://www.investopedia.com/articles/investing/082614/how-stock-market-works.asp">Investopedia</a>
 */
public class TradeAmount extends Indicator<Num> {

    /**
     * Convenience static method for {@link #TradeAmount(BarSeries)}.
     */
    public static TradeAmount tradeAmount(BarSeries barSeries) {
        return new TradeAmount(barSeries);
    }

    private final Ohlc4 ohlc4;
    private final Volume volume;

    /**
     * Instantiates a new {@link TradeAmount}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public TradeAmount(BarSeries barSeries) {
        super(barSeries, 0);
        ohlc4 = new Ohlc4(barSeries);
        volume = new Volume(barSeries);
    }

    @Override
    protected Num calculate(long index) {
        return ohlc4.getValue(index).multiply(volume.getValue(index));
    }
}
