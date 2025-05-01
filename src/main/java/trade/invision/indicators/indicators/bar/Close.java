package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link Close} is a {@link Num} {@link Indicator} to provide {@link Bar#getClose()}.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class Close extends CachelessIndicator<Num> {

    /**
     * Gets a {@link Close}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static Close close(BarSeries barSeries) {
        return new Close(barSeries);
    }

    private final BarSeries barSeries;

    protected Close(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        return barSeries.get(index).getClose();
    }
}
