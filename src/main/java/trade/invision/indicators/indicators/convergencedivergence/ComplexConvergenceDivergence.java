package trade.invision.indicators.indicators.convergencedivergence;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.statistical.CorrelationCoefficient;
import trade.invision.indicators.indicators.statistical.regression.LinearRegression;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.statistical.CorrelationCoefficient.correlationCoefficient;
import static trade.invision.indicators.indicators.statistical.regression.LinearRegression.linearRegressionSlope;

/**
 * {@link ComplexConvergenceDivergence} is a {@link Boolean} {@link Indicator} to test whether two {@link Num}
 * {@link Indicator}s converge or diverge over a <code>length</code> of values. "Complex" convergence-divergence uses an
 * advanced evaluation technique that tests if the correlation (using the correlation coefficient) and the slope (using
 * the linear regression model) of values in a timeframe that align with the given convergence-divergence
 * <code>type</code> are above the given thresholds. This requires more computational power than the "simple"
 * convergence-divergence technique. Internally, this uses the {@link CorrelationCoefficient} and the
 * {@link LinearRegression}.
 *
 * @see <a href="https://www.investopedia.com/terms/d/divergence.asp">Investopedia</a>
 */
public class ComplexConvergenceDivergence extends Indicator<Boolean> {

    /**
     * Gets a {@link ComplexConvergenceDivergence}.
     *
     * @param first                the first {@link Indicator}
     * @param second               the second {@link Indicator}
     * @param type                 the {@link ConvergenceDivergenceType}
     * @param length               the number of values to look back at
     * @param correlationThreshold the correlation threshold. Must be between zero and one. Zero to disable.
     * @param slopeThreshold       the slope threshold. Must be between zero and one. Zero to disable.
     * @param unbiased             <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the
     *                             correlation coefficient calculation, <code>false</code> to use <code>n</code>
     *                             (biased)
     */
    public static ComplexConvergenceDivergence complexConvergenceDivergence(Indicator<Num> first, Indicator<Num> second,
            ConvergenceDivergenceType type, int length, Num correlationThreshold, Num slopeThreshold,
            boolean unbiased) {
        return CACHE.get(new CacheKey(first, second, type, length, correlationThreshold, slopeThreshold, unbiased),
                key -> new ComplexConvergenceDivergence(first, second,
                        type, length, correlationThreshold, slopeThreshold, unbiased));
    }

    private static final Cache<CacheKey, ComplexConvergenceDivergence> CACHE =
            Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> first;
        Indicator<Num> second;
        ConvergenceDivergenceType type;
        int length;
        Num correlationThreshold;
        Num slopeThreshold;
        boolean unbiased;
    }

    private final ConvergenceDivergenceType type;
    private final Num correlationThreshold;
    private final Num slopeThreshold;
    private final Num slopeThresholdNegated;
    private final CorrelationCoefficient correlationCoefficient;
    private final Indicator<Num> firstSlope;
    private final Indicator<Num> secondSlope;

    protected ComplexConvergenceDivergence(Indicator<Num> first, Indicator<Num> second, ConvergenceDivergenceType type,
            int length, Num correlationThreshold, Num slopeThreshold, boolean unbiased) {
        super(first.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        checkArgument(correlationThreshold.isGreaterThan(numOfZero()) && correlationThreshold.isLessThan(numOfOne()),
                "'correlationThreshold' must be between zero and one (inclusive)!");
        checkArgument(slopeThreshold.isGreaterThan(numOfZero()) && slopeThreshold.isLessThan(numOfOne()),
                "'slopeThreshold' must be between zero and one (inclusive)!");
        this.type = type;
        this.correlationThreshold = correlationThreshold;
        this.slopeThreshold = slopeThreshold;
        slopeThresholdNegated = slopeThreshold.negate();
        if (!correlationThreshold.isZero(series.getEpsilon())) {
            correlationCoefficient = correlationCoefficient(first, second, length, unbiased);
        } else {
            correlationCoefficient = null;
        }
        if (!slopeThreshold.isZero(series.getEpsilon())) {
            firstSlope = linearRegressionSlope(first, length);
            secondSlope = linearRegressionSlope(second, length);
        } else {
            firstSlope = null;
            secondSlope = null;
        }
    }

    @Override
    protected Boolean calculate(long index) {
        if (correlationCoefficient != null && correlationCoefficient.getValue(index).isLessThan(correlationThreshold)) {
            return false;
        }
        if (firstSlope != null && secondSlope != null) {
            final Num firstSlopeValue = firstSlope.getValue(index);
            switch (type) {
                case POSITIVE_CONVERGENCE, POSITIVE_DIVERGENCE -> {
                    if (firstSlopeValue.isLessThan(slopeThreshold)) {
                        return false;
                    }
                }
                case NEGATIVE_CONVERGENCE, NEGATIVE_DIVERGENCE -> {
                    if (firstSlopeValue.isGreaterThan(slopeThresholdNegated)) {
                        return false;
                    }
                }
                default -> throw new UnsupportedOperationException();
            }
            final Num secondSlopeValue = secondSlope.getValue(index);
            switch (type) {
                case POSITIVE_CONVERGENCE, POSITIVE_DIVERGENCE -> {
                    return secondSlopeValue.isGreaterThan(slopeThreshold);
                }
                case NEGATIVE_CONVERGENCE, NEGATIVE_DIVERGENCE -> {
                    return secondSlopeValue.isLessThan(slopeThresholdNegated);
                }
                default -> throw new UnsupportedOperationException();
            }
        }
        return true;
    }
}
