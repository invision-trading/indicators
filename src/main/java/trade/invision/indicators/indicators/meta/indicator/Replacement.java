package trade.invision.indicators.indicators.meta.indicator;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;

/**
 * {@link Replacement} is an {@link Indicator} to provide the values of the given <code>replacement</code>
 * {@link Indicator} instead of the values of the given <code>replacee</code> {@link Indicator} only for all the indices
 * that are less than the given <code>replacementIndex</code>.
 *
 * @param <T> the type
 */
public class Replacement<T> extends CachelessIndicator<T> {

    /**
     * Convenience static method for {@link #Replacement(Indicator, Indicator, int)}.
     */
    public static <T> Replacement<T> replacement(Indicator<T> replacee, Indicator<T> replacement,
            int replacementIndex) {
        return new Replacement<>(replacee, replacement, replacementIndex);
    }

    private final Indicator<T> replacee;
    private final Indicator<T> replacement;
    private final int replacementIndex;

    /**
     * Instantiates a new {@link Replacement}.
     *
     * @param replacee         the replacee {@link Indicator}
     * @param replacement      the replacement {@link Indicator}
     * @param replacementIndex the replacement index
     */
    public Replacement(Indicator<T> replacee, Indicator<T> replacement, int replacementIndex) {
        super(replacee.getSeries(), replacement.getMinimumStableIndex());
        this.replacee = replacee;
        this.replacement = replacement;
        this.replacementIndex = replacementIndex;
    }

    @Override
    protected T calculate(long index) {
        return index < replacementIndex ? replacement.getValue(index) : replacee.getValue(index);
    }
}
