package trade.invision.indicators.indicators.previous;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link PreviousDifference} is a {@link Num} {@link Indicator} to provide the current value subtracted by the
 * <i>n</i>-th previous value.
 */
public class PreviousDifference extends Indicator<Num> {

    /**
     * Convenience static method for {@link #PreviousDifference(Indicator)}.
     */
    public static PreviousDifference previousDifference(Indicator<Num> indicator) {
        return new PreviousDifference(indicator);
    }

    /**
     * Convenience static method for {@link #PreviousDifference(Indicator, int)}.
     */
    public static PreviousDifference previousDifference(Indicator<Num> indicator, int n) {
        return new PreviousDifference(indicator, n);
    }

    private final Indicator<Num> indicator;
    private final PreviousValue<Num> previousValue;

    /**
     * Instantiates a new {@link PreviousDifference} with <code>n</code> set to <code>1</code>.
     *
     * @param indicator the {@link Num} {@link Indicator}
     */
    public PreviousDifference(Indicator<Num> indicator) {
        this(indicator, 1);
    }

    /**
     * Instantiates a new {@link PreviousDifference}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public PreviousDifference(Indicator<Num> indicator, int n) {
        super(indicator.getSeries(), n);
        this.indicator = indicator;
        previousValue = new PreviousValue<>(indicator, n);
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).subtract(previousValue.getValue(index));
    }
}
