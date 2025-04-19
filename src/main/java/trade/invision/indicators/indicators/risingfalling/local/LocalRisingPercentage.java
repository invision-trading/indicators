package trade.invision.indicators.indicators.risingfalling.local;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link LocalRisingPercentage} is a {@link Num} {@link Indicator} to provide the percentage of rising values over a
 * <code>length</code> of values. A rising value is defined as a value being greater than its previous value. The
 * percentage is represented as a fractional. For example, a provided value of <code>0.15</code> would represent
 * <code>15%</code>.
 */
public class LocalRisingPercentage extends AbstractLocalRisingFallingPercentage {

    /**
     * Convenience static method for {@link #LocalRisingPercentage(Indicator, int)}.
     */
    public static LocalRisingPercentage localRisingPercentage(Indicator<Num> indicator, int length) {
        return new LocalRisingPercentage(indicator, length);
    }

    /**
     * Instantiates a new {@link LocalRisingPercentage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public LocalRisingPercentage(Indicator<Num> indicator, int length) {
        super(indicator, length, true);
    }
}
