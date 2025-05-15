package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link Low} is a {@link Num} {@link Indicator} to provide {@link Bar#getLow()}.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class Low extends CachelessIndicator<Num> {

    /**
     * @see #low(BarSeries)
     */
    public static Low l(BarSeries barSeries) {
        return low(barSeries);
    }

    /**
     * Gets a {@link Low}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static Low low(BarSeries barSeries) {
        return new Low(barSeries);
    }

    private final BarSeries barSeries;

    protected Low(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        return barSeries.get(index).getLow();
    }
}
