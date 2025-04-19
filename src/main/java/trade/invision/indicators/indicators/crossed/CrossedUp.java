package trade.invision.indicators.indicators.crossed;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static java.lang.Math.max;

/**
 * {@link CrossedUp} is a {@link Boolean} {@link Indicator} to provide a positive crossing signal. A positive crossing
 * occurs when <code>first</code> crosses above <code>second</code>.
 */
public class CrossedUp extends Indicator<Boolean> {

    /**
     * Convenience static method for {@link #CrossedUp(Indicator, Indicator)}.
     */
    public static CrossedUp crossedUp(Indicator<Num> first, Indicator<Num> second) {
        return new CrossedUp(first, second);
    }

    private final Indicator<Num> first;
    private final Indicator<Num> second;

    /**
     * Instantiates a new {@link CrossedUp}.
     *
     * @param first  the first {@link Indicator}
     * @param second the second {@link Indicator}
     */
    public CrossedUp(Indicator<Num> first, Indicator<Num> second) {
        super(first.getSeries(), max(first.getMinimumStableIndex(), second.getMinimumStableIndex()));
        this.first = first.caching();
        this.second = second.caching();
    }

    @Override
    protected Boolean calculate(long index) {
        if (index == 0 || first.getValue(index).isLessThanOrEqual(second.getValue(index), series.getEpsilon())) {
            return false;
        }
        do {
            index--;
        } while (index > 0 && first.getValue(index).isEqual(second.getValue(index), series.getEpsilon()));
        return first.getValue(index).isLessThan(second.getValue(index));
    }
}
