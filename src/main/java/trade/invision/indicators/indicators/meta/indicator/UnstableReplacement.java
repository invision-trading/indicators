package trade.invision.indicators.indicators.meta.indicator;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;

/**
 * {@link UnstableReplacement} is an {@link Indicator} to provide the values of the given <code>replacement</code>
 * {@link Indicator} instead of the values of the given <code>unstable</code> {@link Indicator} only for all the indices
 * that the <code>unstable</code> {@link Indicator} would provide unstable values for, as determined by
 * {@link Indicator#getMinimumStableIndex()}.
 *
 * @param <T> the type
 */
public class UnstableReplacement<T> extends CachelessIndicator<T> {

    /**
     * Convenience static method for {@link #UnstableReplacement(Indicator, Indicator)}.
     */
    public static <T> UnstableReplacement<T> unstableReplacement(Indicator<T> unstable, Indicator<T> replacement) {
        return new UnstableReplacement<>(unstable, replacement);
    }

    private final Indicator<T> unstable;
    private final Indicator<T> replacement;

    /**
     * Instantiates a new {@link UnstableReplacement}.
     *
     * @param unstable    the unstable {@link Indicator}
     * @param replacement the replacement {@link Indicator}
     */
    public UnstableReplacement(Indicator<T> unstable, Indicator<T> replacement) {
        super(unstable.getSeries(), replacement.getMinimumStableIndex());
        this.unstable = unstable;
        this.replacement = replacement;
    }

    @Override
    protected T calculate(long index) {
        return index < unstable.getMinimumStableIndex() ? replacement.getValue(index) : unstable.getValue(index);
    }
}
