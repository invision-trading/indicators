package trade.invision.indicators.indicators.statistical;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.sma.SimpleMovingAverage;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link StandardScore} is a {@link Num} {@link Indicator} to provide the statistical standard score (z-score) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Standard_score">Wikipedia</a>
 */
public class StandardScore extends Indicator<Num> {

    /**
     * @see #standardScore(Indicator, int)
     */
    public static StandardScore zScore(Indicator<Num> indicator, int length) {
        return standardScore(indicator, length);
    }

    /**
     * Convenience static method for {@link #StandardScore(Indicator, int)}.
     */
    public static StandardScore standardScore(Indicator<Num> indicator, int length) {
        return new StandardScore(indicator, length);
    }

    /**
     * @see #standardScore(Indicator, int, boolean)
     */
    public static StandardScore zScore(Indicator<Num> indicator, int length, boolean unbiased) {
        return standardScore(indicator, length, unbiased);
    }

    /**
     * Convenience static method for {@link #StandardScore(Indicator, int, boolean)}.
     */
    public static StandardScore standardScore(Indicator<Num> indicator, int length, boolean unbiased) {
        return new StandardScore(indicator, length, unbiased);
    }

    private final Indicator<Num> indicator;
    private final SimpleMovingAverage sma;
    private final StandardDeviation standardDeviation;

    /**
     * Calls {@link #StandardScore(Indicator, int, boolean)} with <code>unbiased</code> set to <code>true</code>.
     */
    public StandardScore(Indicator<Num> indicator, int length) {
        this(indicator, length, true);
    }

    /**
     * Instantiates a new {@link StandardScore}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param unbiased  <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the
     *                  standard score calculation, <code>false</code> to use <code>n</code> (biased)
     */
    public StandardScore(Indicator<Num> indicator, int length, boolean unbiased) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        sma = new SimpleMovingAverage(indicator, length);
        standardDeviation = new StandardDeviation(indicator, length, unbiased);
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).subtract(sma.getValue(index)).divide(standardDeviation.getValue(index));
    }
}
