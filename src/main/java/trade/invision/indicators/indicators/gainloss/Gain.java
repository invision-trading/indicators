package trade.invision.indicators.indicators.gainloss;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.previous.PreviousDifference;
import trade.invision.num.Num;

/**
 * {@link Gain} is a {@link Num} {@link Indicator} to provide the gain of the current value compared to the
 * <i>n</i>-th previous value. This will always return a positive number or zero.
 */
public class Gain extends PreviousDifference {

    /**
     * Convenience static method for {@link #Gain(Indicator)}.
     */
    public static Gain gain(Indicator<Num> indicator) {
        return new Gain(indicator);
    }

    /**
     * Convenience static method for {@link #Gain(Indicator, int)}.
     */
    public static Gain gain(Indicator<Num> indicator, int n) {
        return new Gain(indicator, n);
    }

    /**
     * Instantiates a new {@link Gain} with <code>n</code> set to <code>1</code>.
     *
     * @param indicator the {@link Num} {@link Indicator}
     */
    public Gain(Indicator<Num> indicator) {
        super(indicator);
    }

    /**
     * Instantiates a new {@link Gain}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public Gain(Indicator<Num> indicator, int n) {
        super(indicator, n);
    }

    @Override
    protected Num calculate(long index) {
        return numOfZero().maximum(super.calculate(index));
    }
}
