package trade.invision.indicators.indicators.ao;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Close;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.indicators.indicators.macd.MovingAverageConvergenceDivergence;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link AwesomeOscillator} is a {@link Num} {@link Indicator} to provide the Awesome Oscillator (AO) over a
 * <code>shortLength</code> and <code>longLength</code> of {@link Bar}s.
 *
 * @see <a href="https://www.tradingview.com/support/solutions/43000501826-awesome-oscillator-ao/">TradingView</a>
 */
public class AwesomeOscillator extends MovingAverageConvergenceDivergence {

    /**
     * @see #awesomeOscillator(BarSeries, int, int, MovingAverageSupplier)
     */
    public static AwesomeOscillator ao(BarSeries barSeries, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        return awesomeOscillator(barSeries, shortLength, longLength, movingAverageSupplier);
    }

    /**
     * Convenience static method for {@link #AwesomeOscillator(BarSeries, int, int, MovingAverageSupplier)}.
     */
    public static AwesomeOscillator awesomeOscillator(BarSeries barSeries, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        return new AwesomeOscillator(barSeries, shortLength, longLength, movingAverageSupplier);
    }

    /**
     * Instantiates a new {@link AwesomeOscillator}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param shortLength           the short averaging length (typically 5)
     * @param longLength            the long averaging length (typically 34)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public AwesomeOscillator(BarSeries barSeries, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        super(new Close(barSeries), shortLength, longLength, movingAverageSupplier);
    }
}
