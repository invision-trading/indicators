package trade.invision.indicators.indicators.ma.ema;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link ExponentialMovingAverage} is a {@link Num} {@link Indicator} to provide an Exponential Moving Average (EMA)
 * over a <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/e/ema.asp">Investopedia</a>
 */
public class ExponentialMovingAverage extends AbstractExponentialMovingAverage {

    /**
     * @see #exponentialMovingAverage(Indicator, int)
     */
    public static ExponentialMovingAverage ema(Indicator<Num> indicator, int length) {
        return exponentialMovingAverage(indicator, length);
    }

    /**
     * Calls {@link #exponentialMovingAverage(Indicator, int, Num)} with <code>smoothing</code> set to <code>2</code>.
     */
    public static ExponentialMovingAverage exponentialMovingAverage(Indicator<Num> indicator, int length) {
        return exponentialMovingAverage(indicator, length, indicator.getSeries().getNumFactory().two());
    }

    /**
     * @see #exponentialMovingAverage(Indicator, int, Num)
     */
    public static ExponentialMovingAverage ema(Indicator<Num> indicator, int length, Num smoothing) {
        return exponentialMovingAverage(indicator, length, smoothing);
    }

    /**
     * Gets a {@link ExponentialMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param smoothing the smoothing factor (typically 2)
     */
    public static ExponentialMovingAverage exponentialMovingAverage(Indicator<Num> indicator, int length,
            Num smoothing) {
        return new ExponentialMovingAverage(indicator, length, smoothing);
    }

    protected ExponentialMovingAverage(Indicator<Num> indicator, int length, Num smoothing) {
        super(indicator, length, smoothing.divide(length + 1));
    }
}
