package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;

import java.time.Instant;

/**
 * {@link BarEnd} is an {@link Instant} {@link Indicator} to provide {@link Bar#getEnd()}.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class BarEnd extends CachelessIndicator<Instant> {

    /**
     * Gets a {@link BarEnd}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static BarEnd barEnd(BarSeries barSeries) {
        return new BarEnd(barSeries);
    }

    private final BarSeries barSeries;

    protected BarEnd(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Instant calculate(long index) {
        return barSeries.get(index).getEnd();
    }
}
