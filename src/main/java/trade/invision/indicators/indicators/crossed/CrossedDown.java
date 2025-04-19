package trade.invision.indicators.indicators.crossed;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link CrossedDown} is a {@link Boolean} {@link Indicator} to provide a negative crossing signal. A negative crossing
 * occurs when <code>first</code> crosses below <code>second</code>.
 */
public class CrossedDown extends CrossedUp {

    /**
     * Convenience static method for {@link #CrossedDown(Indicator, Indicator)}.
     */
    public static CrossedDown crossedDown(Indicator<Num> first, Indicator<Num> second) {
        return new CrossedDown(first, second);
    }

    /**
     * Instantiates a new {@link CrossedDown}.
     *
     * @param first  the first {@link Indicator}
     * @param second the second {@link Indicator}
     */
    public CrossedDown(Indicator<Num> first, Indicator<Num> second) {
        super(second, first);
    }
}
