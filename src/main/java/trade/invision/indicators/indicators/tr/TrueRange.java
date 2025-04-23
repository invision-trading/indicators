package trade.invision.indicators.indicators.tr;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link TrueRange} is a {@link Num} {@link Indicator} to provide the True Range (TR) of a {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/a/atr.asp">Investopedia</a>
 */
public class TrueRange extends Indicator<Num> {

    /**
     * @see #trueRange(BarSeries)
     */
    public static TrueRange tr(BarSeries barSeries) {
        return trueRange(barSeries);
    }

    /**
     * Convenience static method for {@link #TrueRange(BarSeries)}.
     */
    public static TrueRange trueRange(BarSeries barSeries) {
        return new TrueRange(barSeries);
    }

    private final BarSeries barSeries;

    /**
     * Instantiates a new {@link TrueRange}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public TrueRange(BarSeries barSeries) {
        super(barSeries, 1);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar currentBar = barSeries.get(index);
        final Num hl = currentBar.getHigh().subtract(currentBar.getLow()).absoluteValue();
        if (index == 0) {
            return hl;
        }
        final Bar previousBar = barSeries.get(index - 1);
        final Num hc = currentBar.getHigh().subtract(previousBar.getClose()).absoluteValue();
        final Num lc = currentBar.getLow().subtract(previousBar.getClose()).absoluteValue();
        return hl.maximum(hc.maximum(lc));
    }
}
