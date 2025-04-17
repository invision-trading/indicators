package trade.invision.indicators.indicators.previous;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link PreviousValue} is an {@link Indicator} to provide the <i>n</i>-th previous value.
 *
 * @param <T> the type
 */
public class PreviousValue<T> extends CachelessIndicator<T> {

    /**
     * Convenience static method for {@link #PreviousValue(Indicator)}.
     */
    public static <T> PreviousValue<T> previousValue(Indicator<T> indicator) {
        return new PreviousValue<>(indicator);
    }

    /**
     * Convenience static method for {@link #PreviousValue(Indicator, int)}.
     */
    public static <T> PreviousValue<T> previousValue(Indicator<T> indicator, int n) {
        return new PreviousValue<>(indicator, n);
    }

    private final Indicator<T> indicator;
    private final int n;

    /**
     * Instantiates a new {@link PreviousValue} with <code>n</code> set to <code>1</code>.
     *
     * @param indicator the {@link Indicator}
     */
    public PreviousValue(Indicator<T> indicator) {
        this(indicator, 1);
    }

    /**
     * Instantiates a new {@link PreviousValue}.
     *
     * @param indicator the {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public PreviousValue(Indicator<T> indicator, int n) {
        super(indicator.getSeries(), n);
        checkArgument(n > 0, "'n' must be greater than zero!");
        this.indicator = indicator.caching();
        this.n = n;
    }

    @Override
    protected T calculate(long index) {
        return indicator.getValue(max(0, index - n));
    }
}
