package trade.invision.indicators.indicators.meta.indicator;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;

/**
 * {@link IsIndexStable} is a {@link Boolean} {@link Indicator} to check if a given <code>index</code> is greater than
 * or equal to the given {@link Indicator}'s {@link #getMinimumStableIndex()}.
 */
public class IsIndexStable extends CachelessIndicator<Boolean> {

    /**
     * Convenience static method for {@link #IsIndexStable(Indicator)}.
     */
    public static IsIndexStable isIndexStable(Indicator<?> indicator) {
        return new IsIndexStable(indicator);
    }

    private final int stableIndex;

    /**
     * Instantiates a new {@link IsIndexStable}.
     *
     * @param indicator the {@link Indicator}
     */
    public IsIndexStable(Indicator<?> indicator) {
        super(indicator.getSeries(), 0);
        stableIndex = indicator.getMinimumStableIndex();
    }

    @Override
    protected Boolean calculate(long index) {
        return index >= stableIndex;
    }
}
