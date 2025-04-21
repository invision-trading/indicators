package trade.invision.indicators.indicators.ma.ema;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link WellesWilderMovingAverage} is a {@link Num} {@link Indicator} to provide a Welles Wilder Moving Average (WWMA)
 * over a <code>length</code> of values. This is the same as an Exponential Moving Average (EMA) except it uses a
 * <code>multiplier</code> of <code>1 / length</code>. Used in Welles Wilder indicators, like the Relative Strength
 * Index (RSI).
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=WellesMA.htm">FM Labs</a>
 */
public class WellesWilderMovingAverage extends AbstractExponentialMovingAverage {

    /**
     * @see #wellesWilderMovingAverage(Indicator, int)
     */
    public static WellesWilderMovingAverage wwma(Indicator<Num> indicator, int length) {
        return wellesWilderMovingAverage(indicator, length);
    }

    /**
     * Convenience static method for {@link #WellesWilderMovingAverage(Indicator, int)}.
     */
    public static WellesWilderMovingAverage wellesWilderMovingAverage(Indicator<Num> indicator, int length) {
        return new WellesWilderMovingAverage(indicator, length);
    }

    /**
     * Instantiates a new {@link WellesWilderMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public WellesWilderMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator, length, indicator.getSeries().getNumFactory().of(length).reciprocal());
    }
}
