package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
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
     * Convenience static method for {@link #High(BarSeries)}.
     */
    public static High high(BarSeries barSeries) {
        return new High(barSeries);
    }

    private final BarSeries barSeries;

    /**
     * Instantiates a new {@link High}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public High(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        return barSeries.get(index).getHigh();
    }
}
