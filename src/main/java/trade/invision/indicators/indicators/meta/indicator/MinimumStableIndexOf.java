package trade.invision.indicators.indicators.meta.indicator;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.constant.ConstantValue;
import trade.invision.num.Num;

/**
 * {@link MinimumStableIndexOf} is a {@link Num} {@link Indicator} to provide the {@link #getMinimumStableIndex()} of an
 * {@link Indicator}.
 */
public class MinimumStableIndexOf extends ConstantValue<Num> {

    /**
     * Convenience static method for {@link #MinimumStableIndexOf(Indicator)}.
     */
    public static MinimumStableIndexOf minimumStableIndexOf(Indicator<?> indicator) {
        return new MinimumStableIndexOf(indicator);
    }

    /**
     * Instantiates a new {@link MinimumStableIndexOf}.
     *
     * @param indicator the {@link Indicator}
     */
    public MinimumStableIndexOf(Indicator<?> indicator) {
        super(indicator.getSeries(), indicator.getSeries().getNumFactory().of(indicator.getMinimumStableIndex()));
    }
}
