package trade.invision.indicators.indicators.meta.indicator;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.constant.ConstantValue;
import trade.invision.num.Num;

/**
 * {@link GetMinimumStableIndex} is a {@link Num} {@link Indicator} to provide the {@link #getMinimumStableIndex()} of
 * an {@link Indicator}.
 */
public class GetMinimumStableIndex extends ConstantValue<Num> {

    /**
     * Convenience static method for {@link #GetMinimumStableIndex(Indicator)}.
     */
    public static GetMinimumStableIndex getMinimumStableIndex(Indicator<?> indicator) {
        return new GetMinimumStableIndex(indicator);
    }

    /**
     * Instantiates a new {@link GetMinimumStableIndex}.
     *
     * @param indicator the {@link Indicator}
     */
    public GetMinimumStableIndex(Indicator<?> indicator) {
        super(indicator.getSeries(), indicator.getSeries().getNumFactory().of(indicator.getMinimumStableIndex()));
    }
}
