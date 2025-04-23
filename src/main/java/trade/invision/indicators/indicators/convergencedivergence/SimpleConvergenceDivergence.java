package trade.invision.indicators.indicators.convergencedivergence;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.risingfalling.local.LocalFallingPercentage;
import trade.invision.indicators.indicators.risingfalling.local.LocalRisingPercentage;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link SimpleConvergenceDivergence} is a {@link Boolean} {@link Indicator} to test whether two {@link Num}
 * {@link Indicator}s converge or diverge over a <code>length</code> of values. "Simple" convergence-divergence uses a
 * basic evaluation technique that tests if the percentage of values in a timeframe that align with the given
 * convergence-divergence
 * <code>type</code> are above a given threshold. This requires less computational power than "complex"
 * convergence-divergence. Internally, this uses the {@link LocalRisingPercentage} and {@link LocalFallingPercentage}
 * {@link Indicator}s.
 *
 * @see <a href="https://www.investopedia.com/terms/d/divergence.asp">Investopedia</a>
 */
public class SimpleConvergenceDivergence extends Indicator<Boolean> {

    /**
     * Convenience static method for
     * {@link #SimpleConvergenceDivergence(Indicator, Indicator, ConvergenceDivergenceType, int, Num)}.
     */
    public static SimpleConvergenceDivergence simpleConvergenceDivergence(Indicator<Num> first, Indicator<Num> second,
            ConvergenceDivergenceType type, int length, Num percentageThreshold) {
        return new SimpleConvergenceDivergence(first, second, type, length, percentageThreshold);
    }

    private final Indicator<Num> riseFallFirst;
    private final Indicator<Num> riseFallSecond;
    private final Num percentageThreshold;

    /**
     * Instantiates a new {@link SimpleConvergenceDivergence}.
     *
     * @param first               the first {@link Indicator}
     * @param second              the second {@link Indicator}
     * @param type                the {@link ConvergenceDivergenceType}
     * @param length              the number of values to look back at
     * @param percentageThreshold the percentage threshold. The percentage is represented as a fractional. For example,
     *                            a provided value of <code>0.15</code> would represent <code>15%</code>. Must be
     *                            between zero and one.
     */
    public SimpleConvergenceDivergence(Indicator<Num> first, Indicator<Num> second, ConvergenceDivergenceType type,
            int length, Num percentageThreshold) {
        super(first.getSeries(), length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        checkArgument(percentageThreshold.isGreaterThan(numOfZero()) && percentageThreshold.isLessThan(numOfOne()),
                "'percentageThreshold' must be between zero and one (inclusive)!");
        this.percentageThreshold = percentageThreshold;
        switch (type) {
            case POSITIVE_CONVERGENCE -> {
                riseFallFirst = new LocalRisingPercentage(first, length);
                riseFallSecond = new LocalRisingPercentage(second, length);
            }
            case NEGATIVE_CONVERGENCE -> {
                riseFallFirst = new LocalFallingPercentage(first, length);
                riseFallSecond = new LocalFallingPercentage(second, length);
            }
            case POSITIVE_DIVERGENCE -> {
                riseFallFirst = new LocalRisingPercentage(first, length);
                riseFallSecond = new LocalFallingPercentage(second, length);
            }
            case NEGATIVE_DIVERGENCE -> {
                riseFallFirst = new LocalFallingPercentage(first, length);
                riseFallSecond = new LocalRisingPercentage(second, length);
            }
            default -> throw new UnsupportedOperationException();
        }
    }

    @Override
    protected Boolean calculate(long index) {
        return riseFallFirst.getValue(index).isGreaterThanOrEqual(percentageThreshold, series.getEpsilon()) &&
                riseFallSecond.getValue(index).isGreaterThanOrEqual(percentageThreshold, series.getEpsilon());
    }
}
