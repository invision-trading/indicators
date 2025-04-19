package trade.invision.indicators.indicators.risingfalling.local;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link LocalFallingPercentage} is a {@link Num} {@link Indicator} to provide the percentage of falling values over a
 * <code>length</code> of values. A falling value is defined as a value being less than its previous value. The
 * percentage is represented as a fractional. For example, a provided value of <code>0.15</code> would represent
 * <code>15%</code>.
 */
public class LocalFallingPercentage extends AbstractLocalRisingFallingPercentage {

    /**
     * Convenience static method for {@link #LocalFallingPercentage(Indicator, int)}.
     */
    public static LocalFallingPercentage localFallingPercentage(Indicator<Num> indicator, int length) {
        return new LocalFallingPercentage(indicator, length);
    }

    /**
     * Instantiates a new {@link LocalFallingPercentage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public LocalFallingPercentage(Indicator<Num> indicator, int length) {
        super(indicator, length, false);
    }
}
