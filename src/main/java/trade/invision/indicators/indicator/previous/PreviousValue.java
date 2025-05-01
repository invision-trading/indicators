package trade.invision.indicators.indicator.previous;

import trade.invision.indicators.indicator.CachelessIndicator;
import trade.invision.indicators.indicator.Indicator;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link PreviousValue} is an {@link Indicator} to provide the <i>n</i>-th previous value.
 *
 * @param <T> the type
 */
public class PreviousValue<T> extends CachelessIndicator<T> {

    /**
     * Calls {@link #previousValue(Indicator, int)} with <code>n</code> set to <code>1</code>.
     */
    public static <T> PreviousValue<T> previousValue(Indicator<T> indicator) {
        return new PreviousValue<>(indicator, 1);
    }

    /**
     * Gets a {@link PreviousValue}.
     *
     * @param indicator the {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static <T> PreviousValue<T> previousValue(Indicator<T> indicator, int n) {
        return new PreviousValue<>(indicator, n);
    }

    private final Indicator<T> indicator;
    private final int n;

    protected PreviousValue(Indicator<T> indicator, int n) {
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
