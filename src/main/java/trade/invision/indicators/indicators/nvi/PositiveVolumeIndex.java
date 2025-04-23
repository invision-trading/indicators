package trade.invision.indicators.indicators.nvi;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link PositiveVolumeIndex} is a {@link Num} {@link Indicator} to provide the Positive Volume Index (PVI). The
 * initial value is <code>100</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/p/pvi.asp">Investopedia</a>
 */
public class PositiveVolumeIndex extends AbstractPositiveNegativeVolumeIndex {

    /**
     * @see #positiveVolumeIndex(BarSeries)
     */
    public static PositiveVolumeIndex pvi(BarSeries barSeries) {
        return positiveVolumeIndex(barSeries);
    }

    /**
     * Convenience static method for {@link #PositiveVolumeIndex(BarSeries)}.
     */
    public static PositiveVolumeIndex positiveVolumeIndex(BarSeries barSeries) {
        return new PositiveVolumeIndex(barSeries);
    }

    /**
     * Instantiates a new {@link PositiveVolumeIndex}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public PositiveVolumeIndex(BarSeries barSeries) {
        super(barSeries, true);
    }
}
