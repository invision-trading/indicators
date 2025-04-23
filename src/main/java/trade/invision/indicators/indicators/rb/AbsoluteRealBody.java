package trade.invision.indicators.indicators.rb;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link AbsoluteRealBody} is a {@link Num} {@link Indicator} to provide the absolute value of the Real Body (ARB) of a
 * {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/r/realbody.asp">Investopedia</a>
 */
public class AbsoluteRealBody extends RealBody {

    /**
     * @see #absoluteRealBody(BarSeries)
     */
    public static AbsoluteRealBody arb(BarSeries barSeries) {
        return absoluteRealBody(barSeries);
    }

    /**
     * Convenience static method for {@link #AbsoluteRealBody(BarSeries)}.
     */
    public static AbsoluteRealBody absoluteRealBody(BarSeries barSeries) {
        return new AbsoluteRealBody(barSeries);
    }

    /**
     * Instantiates a new {@link AbsoluteRealBody}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public AbsoluteRealBody(BarSeries barSeries) {
        super(barSeries);
    }

    @Override
    protected Num calculate(long index) {
        return super.calculate(index).absoluteValue();
    }
}
