package trade.invision.indicators.indicators.convergencedivergence;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.risingfalling.local.LocalFallingPercentage;
import trade.invision.indicators.indicators.risingfalling.local.LocalRisingPercentage;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.risingfalling.local.LocalFallingPercentage.localFallingPercentage;
import static trade.invision.indicators.indicators.risingfalling.local.LocalRisingPercentage.localRisingPercentage;

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
     * Gets a {@link SimpleConvergenceDivergence}.
     *
     * @param first               the first {@link Indicator}
     * @param second              the second {@link Indicator}
     * @param type                the {@link ConvergenceDivergenceType}
     * @param length              the number of values to look back at
     * @param percentageThreshold the percentage threshold. The percentage is represented as a fractional. For example,
     *                            a provided value of <code>0.15</code> would represent <code>15%</code>. Must be
     *                            between zero and one.
     */
    public static SimpleConvergenceDivergence simpleConvergenceDivergence(Indicator<Num> first, Indicator<Num> second,
            ConvergenceDivergenceType type, int length, Num percentageThreshold) {
        return CACHE.get(new CacheKey(first, second, type, length, percentageThreshold),
                key -> new SimpleConvergenceDivergence(first, second, type, length, percentageThreshold));
    }

    private static final Cache<CacheKey, SimpleConvergenceDivergence> CACHE =
            Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> first;
        Indicator<Num> second;
        ConvergenceDivergenceType type;
        int length;
        Num percentageThreshold;
    }

    private final Indicator<Num> riseFallFirst;
    private final Indicator<Num> riseFallSecond;
    private final Num percentageThreshold;

    protected SimpleConvergenceDivergence(Indicator<Num> first, Indicator<Num> second, ConvergenceDivergenceType type,
            int length, Num percentageThreshold) {
        super(first.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        checkArgument(percentageThreshold.isGreaterThan(numOfZero()) && percentageThreshold.isLessThan(numOfOne()),
                "'percentageThreshold' must be between zero and one (inclusive)!");
        this.percentageThreshold = percentageThreshold;
        switch (type) {
            case POSITIVE_CONVERGENCE -> {
                riseFallFirst = localRisingPercentage(first, length);
                riseFallSecond = localRisingPercentage(second, length);
            }
            case NEGATIVE_CONVERGENCE -> {
                riseFallFirst = localFallingPercentage(first, length);
                riseFallSecond = localFallingPercentage(second, length);
            }
            case POSITIVE_DIVERGENCE -> {
                riseFallFirst = localRisingPercentage(first, length);
                riseFallSecond = localFallingPercentage(second, length);
            }
            case NEGATIVE_DIVERGENCE -> {
                riseFallFirst = localFallingPercentage(first, length);
                riseFallSecond = localRisingPercentage(second, length);
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
