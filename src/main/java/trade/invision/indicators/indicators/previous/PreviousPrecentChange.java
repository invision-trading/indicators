package trade.invision.indicators.indicators.previous;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link PreviousPrecentChange} is a {@link Num} {@link Indicator} to provide the percent change of the current value
 * from the <i>n</i>-th previous value. This is also referred to as Rate of Change (ROC) or Momentum. The percentage is
 * represented as a fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 */
public class PreviousPrecentChange extends PreviousRatio {

    /**
     * Convenience static method for {@link #PreviousPrecentChange(Indicator)}.
     */
    public static PreviousPrecentChange previousPrecentChange(Indicator<Num> indicator) {
        return new PreviousPrecentChange(indicator);
    }

    /**
     * Convenience static method for {@link #PreviousPrecentChange(Indicator, int)}.
     */
    public static PreviousPrecentChange previousPrecentChange(Indicator<Num> indicator, int n) {
        return new PreviousPrecentChange(indicator, n);
    }

    /**
     * Instantiates a new {@link PreviousPrecentChange} with <code>n</code> set to <code>1</code>.
     *
     * @param indicator the {@link Num} {@link Indicator}
     */
    public PreviousPrecentChange(Indicator<Num> indicator) {
        this(indicator, 1);
    }

    /**
     * Instantiates a new {@link PreviousPrecentChange}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public PreviousPrecentChange(Indicator<Num> indicator, int n) {
        super(indicator, n);
    }

    @Override
    protected Num calculate(long index) {
        return super.calculate(index).decrement();
    }
}
