package trade.invision.indicators.indicators.ma.hma;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.wma.WeightedMovingAverage;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.rint;
import static java.lang.Math.sqrt;

/**
 * {@link HullMovingAverage} is a {@link Num} {@link Indicator} to provide a Hull Moving Average (HMA) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://alanhull.com/hull-moving-average">Alan Hull</a>
 */
public class HullMovingAverage extends Indicator<Num> {

    /**
     * @see #hullMovingAverage(Indicator, int)
     */
    public static HullMovingAverage hma(Indicator<Num> indicator, int length) {
        return hullMovingAverage(indicator, length);
    }

    /**
     * Convenience static method for {@link #HullMovingAverage(Indicator, int)}.
     */
    public static HullMovingAverage hullMovingAverage(Indicator<Num> indicator, int length) {
        return new HullMovingAverage(indicator, length);
    }

    private final WeightedMovingAverage hma;

    /**
     * Instantiates a new {@link HullMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public HullMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        final WeightedMovingAverage halfWma = new WeightedMovingAverage(indicator, (int) rint(length / 2.0));
        final WeightedMovingAverage wma = new WeightedMovingAverage(indicator, length);
        hma = new WeightedMovingAverage(new Indicator<>(series, 0) {
            @Override
            protected Num calculate(long index) {
                return halfWma.getValue(index).multiply(numOfTwo()).subtract(wma.getValue(index));
            }
        }, (int) rint(sqrt(length)));
    }

    @Override
    protected Num calculate(long index) {
        return hma.getValue(index);
    }
}
