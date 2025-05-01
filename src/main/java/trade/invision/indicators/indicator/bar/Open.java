package trade.invision.indicators.indicator.bar;

import trade.invision.indicators.indicator.CachelessIndicator;
import trade.invision.indicators.indicator.Indicator;
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
     * Gets a {@link Open}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static Open open(BarSeries barSeries) {
        return new Open(barSeries);
    }

    private final BarSeries barSeries;

    protected Open(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        return barSeries.get(index).getOpen();
    }
}
