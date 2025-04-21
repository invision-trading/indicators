package trade.invision.indicators.indicators.statistical;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link StandardError} is a {@link Num} {@link Indicator} to provide the statistical standard error (stderr) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Standard_error">Wikipedia</a>
 */
public class StandardError extends Indicator<Num> {

    /**
     * @see #standardError(Indicator, int)
     */
    public static StandardError stderr(Indicator<Num> indicator, int length) {
        return standardError(indicator, length);
    }

    /**
     * Convenience static method for {@link #StandardError(Indicator, int)}.
     */
    public static StandardError standardError(Indicator<Num> indicator, int length) {
        return new StandardError(indicator, length);
    }

    /**
     * @see #standardError(Indicator, int, boolean)
     */
    public static StandardError stderr(Indicator<Num> indicator, int length, boolean unbiased) {
        return standardError(indicator, length, unbiased);
    }

    /**
     * Convenience static method for {@link #StandardError(Indicator, int, boolean)}.
     */
    public static StandardError standardError(Indicator<Num> indicator, int length, boolean unbiased) {
        return new StandardError(indicator, length, unbiased);
    }

    private final int length;
    private final StandardDeviation standardDeviation;

    /**
     * Calls {@link #StandardError(Indicator, int, boolean)} with <code>unbiased</code> set to <code>true</code>.
     */
    public StandardError(Indicator<Num> indicator, int length) {
        this(indicator, length, true);
    }

    /**
     * Instantiates a new {@link StandardError}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param unbiased  <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the
     *                  standard error calculation, <code>false</code> to use <code>n</code> (biased)
     */
    public StandardError(Indicator<Num> indicator, int length, boolean unbiased) {
        super(indicator.getSeries(), length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.length = length;
        standardDeviation = new StandardDeviation(indicator, length, unbiased);
    }

    @Override
    protected Num calculate(long index) {
        final long startIndex = max(0, index - length + 1);
        final long observations = index - startIndex + 1;
        return standardDeviation.getValue(index).divide(numOf(observations).squareRoot());
    }
}
