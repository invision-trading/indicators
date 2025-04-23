package trade.invision.indicators.indicators.ao;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Close;
import trade.invision.indicators.indicators.macd.MovingAverageConvergenceDivergence;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import java.util.function.BiFunction;

/**
 * {@link AwesomeOscillator} is a {@link Num} {@link Indicator} to provide the Awesome Oscillator (AO) over a
 * <code>shortLength</code> and <code>longLength</code> of {@link Bar}s.
 *
 * @see <a href="https://www.tradingview.com/support/solutions/43000501826-awesome-oscillator-ao/">TradingView</a>
 */
public class AwesomeOscillator extends MovingAverageConvergenceDivergence {

    /**
     * @see #awesomeOscillator(BarSeries, int, int, BiFunction)
     */
    public static AwesomeOscillator ao(BarSeries barSeries, int shortLength, int longLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return awesomeOscillator(barSeries, shortLength, longLength, averagingIndicatorSupplier);
    }

    /**
     * Convenience static method for {@link #AwesomeOscillator(BarSeries, int, int, BiFunction)}.
     */
    public static AwesomeOscillator awesomeOscillator(BarSeries barSeries, int shortLength, int longLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return new AwesomeOscillator(barSeries, shortLength, longLength, averagingIndicatorSupplier);
    }

    /**
     * Instantiates a new {@link AwesomeOscillator}.
     *
     * @param barSeries                  the {@link BarSeries}
     * @param shortLength                the short averaging length (typically 5)
     * @param longLength                 the long averaging length (typically 34)
     * @param averagingIndicatorSupplier the {@link BiFunction} to supply the averaging {@link Indicator}
     */
    public AwesomeOscillator(BarSeries barSeries, int shortLength, int longLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        super(new Close(barSeries), shortLength, longLength, averagingIndicatorSupplier);
    }
}
