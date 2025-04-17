package trade.invision.indicators.indicators.barprice;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link Hlc3} is a {@link Num} {@link Indicator} to provide the HLC/3 price of a {@link Bar}. This is also known as
 * the "Typical Price"
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=TypicalPrices.htm">FM Labs</a>
 */
public class Hlc3 extends Indicator<Num> {

    /**
     * Convenience static method for {@link #Hlc3(BarSeries)}.
     */
    public static Hlc3 hlc3(BarSeries barSeries) {
        return new Hlc3(barSeries);
    }

    private final BarSeries barSeries;

    /**
     * Instantiates a new {@link Hlc3}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public Hlc3(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return bar.getHigh().add(bar.getLow()).add(bar.getClose()).divide(numOfThree());
    }
}
