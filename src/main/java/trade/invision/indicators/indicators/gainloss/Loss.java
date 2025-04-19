package trade.invision.indicators.indicators.gainloss;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.previous.PreviousDifference;
import trade.invision.num.Num;

/**
 * {@link Loss} is a {@link Num} {@link Indicator} to provide the loss of the current value compared to the
 * <i>n</i>-th previous value. This will always return a positive number or zero.
 */
public class Loss extends PreviousDifference {

    /**
     * Convenience static method for {@link #Loss(Indicator)}.
     */
    public static Loss loss(Indicator<Num> indicator) {
        return new Loss(indicator);
    }

    /**
     * Convenience static method for {@link #Loss(Indicator, int)}.
     */
    public static Loss loss(Indicator<Num> indicator, int n) {
        return new Loss(indicator, n);
    }

    /**
     * Instantiates a new {@link Loss} with <code>n</code> set to <code>1</code>.
     *
     * @param indicator the {@link Num} {@link Indicator}
     */
    public Loss(Indicator<Num> indicator) {
        super(indicator);
    }

    /**
     * Instantiates a new {@link Loss}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public Loss(Indicator<Num> indicator, int n) {
        super(indicator, n);
    }

    @Override
    protected Num calculate(long index) {
        return numOfZero().minimum(super.calculate(index)).negate();
    }
}
