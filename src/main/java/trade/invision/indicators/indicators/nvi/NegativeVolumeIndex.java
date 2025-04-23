package trade.invision.indicators.indicators.nvi;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link NegativeVolumeIndex} is a {@link Num} {@link Indicator} to provide the Negative Volume Index (NVI). The
 * initial value is <code>100</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/n/nvi.asp">Investopedia</a>
 */
public class NegativeVolumeIndex extends AbstractPositiveNegativeVolumeIndex {

    /**
     * @see #negativeVolumeIndex(BarSeries)
     */
    public static NegativeVolumeIndex pvi(BarSeries barSeries) {
        return negativeVolumeIndex(barSeries);
    }

    /**
     * Convenience static method for {@link #NegativeVolumeIndex(BarSeries)}.
     */
    public static NegativeVolumeIndex negativeVolumeIndex(BarSeries barSeries) {
        return new NegativeVolumeIndex(barSeries);
    }

    /**
     * Instantiates a new {@link NegativeVolumeIndex}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public NegativeVolumeIndex(BarSeries barSeries) {
        super(barSeries, false);
    }
}
