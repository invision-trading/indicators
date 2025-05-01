package trade.invision.indicators.indicator.meta.indicator;

import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.constant.ConstantValue;
import trade.invision.num.Num;

/**
 * {@link GetMinimumStableIndex} is a {@link Num} {@link Indicator} to provide the {@link #getMinimumStableIndex()} of
 * an {@link Indicator}.
 */
public class GetMinimumStableIndex extends ConstantValue<Num> {

    /**
     * Gets a {@link GetMinimumStableIndex}.
     *
     * @param indicator the {@link Indicator}
     */
    public static GetMinimumStableIndex getMinimumStableIndex(Indicator<?> indicator) {
        return new GetMinimumStableIndex(indicator);
    }

    protected GetMinimumStableIndex(Indicator<?> indicator) {
        super(indicator.getSeries(), indicator.getSeries().getNumFactory().of(indicator.getMinimumStableIndex()));
    }
}
