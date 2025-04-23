package trade.invision.indicators.indicators.statistical;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link CorrelationCoefficient} is a {@link Num} {@link Indicator} to provide the statistical correlation coefficient
 * (CC) of two {@link Indicator}s over a <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Pearson_correlation_coefficient">Wikipedia</a>
 */
public class CorrelationCoefficient extends Indicator<Num> {

    /**
     * @see #correlationCoefficient(Indicator, Indicator, int, boolean)
     */
    public static CorrelationCoefficient cc(Indicator<Num> first, Indicator<Num> second, int length, boolean unbiased) {
        return correlationCoefficient(first, second, length, unbiased);
    }

    /**
     * Convenience static method for {@link #CorrelationCoefficient(Indicator, Indicator, int, boolean)}.
     */
    public static CorrelationCoefficient correlationCoefficient(Indicator<Num> first, Indicator<Num> second, int length,
            boolean unbiased) {
        return new CorrelationCoefficient(first, second, length, unbiased);
    }

    private final Variance variance1;
    private final Variance variance2;
    private final Covariance covariance;

    /**
     * Instantiates a new {@link CorrelationCoefficient}.
     *
     * @param first    the first {@link Indicator}
     * @param second   the second {@link Indicator}
     * @param length   the number of values to look back at
     * @param unbiased <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the variance and
     *                 covariance calculations, <code>false</code> to use <code>n</code> (biased)
     */
    public CorrelationCoefficient(Indicator<Num> first, Indicator<Num> second, int length, boolean unbiased) {
        super(first.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        variance1 = new Variance(first, length, unbiased);
        variance2 = new Variance(second, length, unbiased);
        covariance = new Covariance(first, second, length, unbiased);
    }

    @Override
    protected Num calculate(long index) {
        return covariance.getValue(index)
                .divide(variance1.getValue(index).multiply(variance2.getValue(index)).squareRoot());
    }
}
