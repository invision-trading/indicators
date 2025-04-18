package trade.invision.indicators.indicators.barprice;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link Ohlc4} is a {@link Num} {@link Indicator} to provide the OHLC/4 price of a {@link Bar}. This is also known as
 * the "Average Price"
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=AvgPrices.htm">FM Labs</a>
 */
public class Ohlc4 extends Indicator<Num> {

    /**
     * @see #ohlc4(BarSeries)
     */
    public static Ohlc4 averagePrice(BarSeries barSeries) {
        return ohlc4(barSeries);
    }

    /**
     * Convenience static method for {@link #Ohlc4(BarSeries)}.
     */
    public static Ohlc4 ohlc4(BarSeries barSeries) {
        return new Ohlc4(barSeries);
    }

    private final BarSeries barSeries;

    /**
     * Instantiates a new {@link Ohlc4}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public Ohlc4(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return bar.getOpen().add(bar.getHigh()).add(bar.getLow()).add(bar.getClose()).divide(numOfFour());
    }
}
