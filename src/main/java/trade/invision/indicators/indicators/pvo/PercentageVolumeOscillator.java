package trade.invision.indicators.indicators.pvo;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.ppo.PercentagePriceOscillator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import java.util.function.BiFunction;

/**
 * {@link PercentageVolumeOscillator} is a {@link Num} {@link Indicator} to provide the Percentage Volume Oscillator
 * (PVO) over a <code>shortLength</code> and <code>longLength</code> of {@link Bar}s. This uses the
 * {@link PercentagePriceOscillator} with the {@link Volume} {@link Indicator}.
 *
 * @see <a
 * href="https://chartschool.stockcharts.com/table-of-contents/technical-indicators-and-overlays/technical-indicators/percentage-volume-oscillator-pvo">StockCharts</a>
 */
public class PercentageVolumeOscillator extends PercentagePriceOscillator {

    /**
     * @see #percentageVolumeOscillator(BarSeries, int, int, BiFunction)
     */
    public static PercentageVolumeOscillator pvo(BarSeries barSeries, int shortLength, int longLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return percentageVolumeOscillator(barSeries, shortLength, longLength, averagingIndicatorSupplier);
    }

    /**
     * Convenience static method for {@link #PercentageVolumeOscillator(BarSeries, int, int, BiFunction)}.
     */
    public static PercentageVolumeOscillator percentageVolumeOscillator(BarSeries barSeries,
            int shortLength, int longLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return new PercentageVolumeOscillator(barSeries, shortLength, longLength, averagingIndicatorSupplier);
    }

    /**
     * Instantiates a new {@link PercentageVolumeOscillator}.
     *
     * @param barSeries                  the {@link BarSeries}
     * @param shortLength                the short averaging length (typically 12)
     * @param longLength                 the long averaging length (typically 26)
     * @param averagingIndicatorSupplier the {@link BiFunction} to supply the averaging {@link Indicator}
     */
    public PercentageVolumeOscillator(BarSeries barSeries, int shortLength, int longLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        super(new Volume(barSeries), shortLength, longLength, averagingIndicatorSupplier);
    }
}
