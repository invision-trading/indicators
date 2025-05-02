package trade.invision.indicators.indicator.bar;

import trade.invision.indicators.indicator.CachelessIndicator;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link High} is a {@link Num} {@link Indicator} to provide {@link Bar#getHigh()}.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class High extends CachelessIndicator<Num> {

    /**
     * @see #high(BarSeries)
     */
    public static High h(BarSeries barSeries) {
        return high(barSeries);
    }

    /**
     * Gets a {@link High}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static High high(BarSeries barSeries) {
        return new High(barSeries);
    }

    private final BarSeries barSeries;

    protected High(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        return barSeries.get(index).getHigh();
    }
}
