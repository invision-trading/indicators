package trade.invision.indicators.indicators.statistical;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.cumulative.CumulativeSum;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.min;
import static trade.invision.indicators.indicators.operation.unary.NumUnaryOperations.square;

/**
 * {@link RootMeanSquare} is a {@link Num} {@link Indicator} to provide the statistical root-mean-square (RMS) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Root_mean_square">Wikipedia</a>
 */
public class RootMeanSquare extends Indicator<Num> {

    /**
     * @see #rootMeanSquare(Indicator, int)
     */
    public static RootMeanSquare rms(Indicator<Num> indicator, int length) {
        return rootMeanSquare(indicator, length);
    }

    /**
     * Convenience static method for {@link #RootMeanSquare(Indicator, int)}.
     */
    public static RootMeanSquare rootMeanSquare(Indicator<Num> indicator, int length) {
        return new RootMeanSquare(indicator, length);
    }

    private final int length;
    private final CumulativeSum squaredSum;

    /**
     * Instantiates a new {@link RootMeanSquare}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public RootMeanSquare(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.length = length;
        squaredSum = new CumulativeSum(square(indicator), length);
    }

    @Override
    protected Num calculate(long index) {
        return squaredSum.getValue(index).divide(min(index + 1, length)).squareRoot();
    }
}
