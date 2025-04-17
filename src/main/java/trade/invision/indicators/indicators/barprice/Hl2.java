package trade.invision.indicators.indicators.barprice;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link Hl2} is a {@link Num} {@link Indicator} to provide the HL/2 price of a {@link Bar}. This is also known as the
 * "Median Price"
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=MedianPrices.htm">FM Labs</a>
 */
public class Hl2 extends Indicator<Num> {

    /**
     * Convenience static method for {@link #Hl2(BarSeries)}.
     */
    public static Hl2 hl2(BarSeries barSeries) {
        return new Hl2(barSeries);
    }

    private final BarSeries barSeries;

    /**
     * Instantiates a new {@link Hl2}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public Hl2(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return bar.getHigh().add(bar.getLow()).divide(numOfTwo());
    }
}
