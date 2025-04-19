package trade.invision.indicators.indicators.risingfalling.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link GlobalFallingPercentage} is a {@link Num} {@link Indicator} to provide the percentage of falling values over
 * all values. A falling value is defined as a value being less than its previous value. The percentage is represented
 * as a fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 */
public class GlobalFallingPercentage extends AbstractGlobalRisingFallingPercentage {

    /**
     * Convenience static method for {@link #GlobalFallingPercentage(Indicator)}.
     */
    public static GlobalFallingPercentage globalFallingPercentage(Indicator<Num> indicator) {
        return new GlobalFallingPercentage(indicator);
    }

    /**
     * Instantiates a new {@link GlobalFallingPercentage}.
     *
     * @param indicator the {@link Indicator}
     */
    public GlobalFallingPercentage(Indicator<Num> indicator) {
        super(indicator, false);
    }
}
