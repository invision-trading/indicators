package trade.invision.indicators.indicator.meta.indicator;

import trade.invision.indicators.indicator.CachelessIndicator;
import trade.invision.indicators.indicator.Indicator;

/**
 * {@link IsIndexStable} is a {@link Boolean} {@link Indicator} to check if a given <code>index</code> is greater than
 * or equal to the given {@link Indicator}'s {@link #getMinimumStableIndex()}.
 */
public class IsIndexStable extends CachelessIndicator<Boolean> {

    /**
     * Gets a {@link IsIndexStable}.
     *
     * @param indicator the {@link Indicator}
     */
    public static IsIndexStable isIndexStable(Indicator<?> indicator) {
        return new IsIndexStable(indicator);
    }

    private final int stableIndex;

    protected IsIndexStable(Indicator<?> indicator) {
        super(indicator.getSeries(), 0);
        stableIndex = indicator.getMinimumStableIndex();
    }

    @Override
    protected Boolean calculate(long index) {
        return index >= stableIndex;
    }
}
