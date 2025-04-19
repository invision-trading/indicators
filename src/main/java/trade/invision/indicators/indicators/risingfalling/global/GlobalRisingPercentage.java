package trade.invision.indicators.indicators.risingfalling.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link GlobalRisingPercentage} is a {@link Num} {@link Indicator} to provide the percentage of rising values over all
 * values. A rising value is defined as a value being greater than its previous value. The percentage is represented as
 * a fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 */
public class GlobalRisingPercentage extends AbstractGlobalRisingFallingPercentage {

    /**
     * Convenience static method for {@link #GlobalRisingPercentage(Indicator)}.
     */
    public static GlobalRisingPercentage globalRisingPercentage(Indicator<Num> indicator) {
        return new GlobalRisingPercentage(indicator);
    }

    /**
     * Instantiates a new {@link GlobalRisingPercentage}.
     *
     * @param indicator the {@link Indicator}
     */
    public GlobalRisingPercentage(Indicator<Num> indicator) {
        super(indicator, true);
    }
}
