package trade.invision.indicators.indicators.rb;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link RealBody} is a {@link Num} {@link Indicator} to provide the Real Body (RB) of a {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/r/realbody.asp">Investopedia</a>
 */
public class RealBody extends Indicator<Num> {

    /**
     * @see #realBody(BarSeries)
     */
    public static RealBody rb(BarSeries barSeries) {
        return realBody(barSeries);
    }

    /**
     * Convenience static method for {@link #RealBody(BarSeries)}.
     */
    public static RealBody realBody(BarSeries barSeries) {
        return new RealBody(barSeries);
    }

    private final BarSeries barSeries;

    /**
     * Instantiates a new {@link RealBody}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public RealBody(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return bar.getClose().subtract(bar.getOpen());
    }
}
