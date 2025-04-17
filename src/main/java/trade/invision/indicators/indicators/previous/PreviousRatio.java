package trade.invision.indicators.indicators.previous;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link PreviousRatio} is a {@link Num} {@link Indicator} to provide the current value divided by the
 * <i>n</i>-th previous value.
 */
public class PreviousRatio extends Indicator<Num> {

    /**
     * Convenience static method for {@link #PreviousRatio(Indicator)}.
     */
    public static PreviousRatio previousRatio(Indicator<Num> indicator) {
        return new PreviousRatio(indicator);
    }

    /**
     * Convenience static method for {@link #PreviousRatio(Indicator, int)}.
     */
    public static PreviousRatio previousRatio(Indicator<Num> indicator, int n) {
        return new PreviousRatio(indicator, n);
    }

    private final Indicator<Num> indicator;
    private final PreviousValue<Num> previousValue;

    /**
     * Instantiates a new {@link PreviousRatio} with <code>n</code> set to <code>1</code>.
     *
     * @param indicator the {@link Num} {@link Indicator}
     */
    public PreviousRatio(Indicator<Num> indicator) {
        this(indicator, 1);
    }

    /**
     * Instantiates a new {@link PreviousRatio}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public PreviousRatio(Indicator<Num> indicator, int n) {
        super(indicator.getSeries(), n);
        this.indicator = indicator;
        previousValue = new PreviousValue<>(indicator, n);
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).divide(previousValue.getValue(index));
    }
}
