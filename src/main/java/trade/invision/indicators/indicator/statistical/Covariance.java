package trade.invision.indicators.indicator.statistical;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.ma.sma.SimpleMovingAverage;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static trade.invision.indicators.indicator.ma.sma.SimpleMovingAverage.simpleMovingAverage;

/**
 * {@link Covariance} is a {@link Num} {@link Indicator} to provide the statistical covariance (covar) of two
 * {@link Indicator}s over a <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Covariance">Wikipedia</a>
 */
public class Covariance extends Indicator<Num> {

    /**
     * @see #covariance(Indicator, Indicator, int, boolean)
     */
    public static Covariance covar(Indicator<Num> first, Indicator<Num> second, int length, boolean unbiased) {
        return covariance(first, second, length, unbiased);
    }

    /**
     * Gets a {@link Covariance}.
     *
     * @param first    the first {@link Indicator}
     * @param second   the second {@link Indicator}
     * @param length   the number of values to look back at
     * @param unbiased <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the covariance
     *                 calculation, <code>false</code> to use <code>n</code> (biased)
     */
    public static Covariance covariance(Indicator<Num> first, Indicator<Num> second, int length, boolean unbiased) {
        return CACHE.get(new CacheKey(first, second, length, unbiased),
                key -> new Covariance(first, second, length, unbiased));
    }

    private static final Cache<CacheKey, Covariance> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> first;
        Indicator<Num> second;
        int length;
        boolean unbiased;
    }

    private final Indicator<Num> first;
    private final Indicator<Num> second;
    private final int length;
    private final boolean unbiased;
    private final SimpleMovingAverage sma1;
    private final SimpleMovingAverage sma2;

    protected Covariance(Indicator<Num> first, Indicator<Num> second, int length, boolean unbiased) {
        super(first.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.first = first.caching();
        this.second = second.caching();
        this.length = length;
        this.unbiased = unbiased;
        sma1 = simpleMovingAverage(first, length);
        sma2 = simpleMovingAverage(second, length);
    }

    @Override
    protected Num calculate(long index) {
        final long startIndex = max(0, index - length + 1);
        final long observations = index - startIndex + 1;
        final Num average1 = sma1.getValue(index);
        final Num average2 = sma2.getValue(index);
        Num covariance = numOfZero();
        for (long indicatorIndex = startIndex; indicatorIndex <= index; indicatorIndex++) {
            covariance = covariance.add(first.getValue(indicatorIndex).subtract(average1)
                    .multiply(second.getValue(indicatorIndex).subtract(average2)));
        }
        return covariance.divide(unbiased ? max(1, observations - 1) : observations);
    }
}
