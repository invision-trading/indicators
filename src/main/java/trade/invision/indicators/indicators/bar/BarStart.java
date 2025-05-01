package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;

import java.time.Instant;

/**
 * {@link BarStart} is an {@link Instant} {@link Indicator} to provide {@link Bar#getStart()}.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class BarStart extends CachelessIndicator<Instant> {

    /**
     * Gets a {@link BarStart}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static BarStart barStart(BarSeries barSeries) {
        return new BarStart(barSeries);
    }

    private final BarSeries barSeries;

    protected BarStart(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Instant calculate(long index) {
        return barSeries.get(index).getStart();
    }
}
