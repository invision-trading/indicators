package trade.invision.indicators.indicators.statistical;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link StandardDeviation} is a {@link Num} {@link Indicator} to provide the statistical standard deviation (stddev)
 * over a <code>length</code> of values. The calculation is the same as the root-mean-square deviation (RMSD).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Standard_deviation">Wikipedia</a>
 */
public class StandardDeviation extends Indicator<Num> {

    /**
     * @see #standardDeviation(Indicator, int)
     */
    public static StandardDeviation stddev(Indicator<Num> indicator, int length) {
        return standardDeviation(indicator, length);
    }

    /**
     * Convenience static method for {@link #StandardDeviation(Indicator, int)}.
     */
    public static StandardDeviation standardDeviation(Indicator<Num> indicator, int length) {
        return new StandardDeviation(indicator, length);
    }

    /**
     * @see #standardDeviation(Indicator, int, boolean)
     */
    public static StandardDeviation stddev(Indicator<Num> indicator, int length, boolean unbiased) {
        return standardDeviation(indicator, length, unbiased);
    }

    /**
     * Convenience static method for {@link #StandardDeviation(Indicator, int, boolean)}.
     */
    public static StandardDeviation standardDeviation(Indicator<Num> indicator, int length, boolean unbiased) {
        return new StandardDeviation(indicator, length, unbiased);
    }

    private final Variance variance;

    /**
     * Calls {@link #StandardDeviation(Indicator, int, boolean)} with <code>unbiased</code> set to <code>true</code>.
     */
    public StandardDeviation(Indicator<Num> indicator, int length) {
        this(indicator, length, true);
    }

    /**
     * Instantiates a new {@link StandardDeviation}.
     *
     * @param indicator the {@link Indicator}
     * @param unbiased  <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the standard
     *                  deviation calculation, <code>false</code> to use <code>n</code> (biased)
     */
    public StandardDeviation(Indicator<Num> indicator, int length, boolean unbiased) {
        super(indicator.getSeries(), length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        variance = new Variance(indicator, length, unbiased);
    }

    @Override
    protected Num calculate(long index) {
        return variance.getValue(index).squareRoot();
    }
}
