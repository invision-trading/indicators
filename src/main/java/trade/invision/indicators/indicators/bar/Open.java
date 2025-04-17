package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link Open} is a {@link Num} {@link Indicator} to provide {@link Bar#getOpen()}.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class Open extends CachelessIndicator<Num> {

    /**
     * Convenience static method for {@link #Open(BarSeries)}.
     */
    public static Open open(BarSeries barSeries) {
        return new Open(barSeries);
    }

    private final BarSeries barSeries;

    /**
     * Instantiates a new {@link Open}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public Open(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        return barSeries.get(index).getOpen();
    }
}
