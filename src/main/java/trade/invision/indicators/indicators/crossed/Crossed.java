package trade.invision.indicators.indicators.crossed;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static java.lang.Math.max;

/**
 * {@link Crossed} is a {@link Boolean} {@link Indicator} to provide a positive or negative crossing signal. A crossing
 * occurs when <code>first</code> crosses above or below <code>second</code>.
 */
public class Crossed extends Indicator<Boolean> {

    /**
     * Convenience static method for {@link #Crossed(Indicator, Indicator)}.
     */
    public static Crossed crossed(Indicator<Num> first, Indicator<Num> second) {
        return new Crossed(first, second);
    }

    private final CrossedUp crossedUp;
    private final CrossedDown crossedDown;

    /**
     * Instantiates a new {@link Crossed}.
     *
     * @param first  the first {@link Indicator}
     * @param second the second {@link Indicator}
     */
    public Crossed(Indicator<Num> first, Indicator<Num> second) {
        super(first.getSeries(), max(first.getMinimumStableIndex(), second.getMinimumStableIndex()));
        crossedUp = new CrossedUp(first, second);
        crossedDown = new CrossedDown(first, second);
    }

    @Override
    protected Boolean calculate(long index) {
        return crossedUp.getValue(index) || crossedDown.getValue(index);
    }
}
