package trade.invision.indicators.indicators.statistical;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.sma.SimpleMovingAverage;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static trade.invision.indicators.indicators.ma.sma.SimpleMovingAverage.simpleMovingAverage;

/**
 * {@link Variance} is a {@link Num} {@link Indicator} to provide the statistical variance (var) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Variance">Wikipedia</a>
 */
public class Variance extends Indicator<Num> {

    /**
     * @see #variance(Indicator, int, boolean)
     */
    public static Variance var(Indicator<Num> indicator, int length, boolean unbiased) {
        return variance(indicator, length, unbiased);
    }

    /**
     * Gets a {@link Variance}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param unbiased  <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the variance
     *                  calculation, <code>false</code> to use <code>n</code> (biased)
     */
    public static Variance variance(Indicator<Num> indicator, int length, boolean unbiased) {
        return CACHE.get(new CacheKey(indicator, length, unbiased), key -> new Variance(indicator, length, unbiased));
    }

    private static final Cache<CacheKey, Variance> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
        boolean unbiased;
    }

    private final Indicator<Num> indicator;
    private final int length;
    private final boolean unbiased;
    private final SimpleMovingAverage sma;

    protected Variance(Indicator<Num> indicator, int length, boolean unbiased) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
        this.unbiased = unbiased;
        sma = simpleMovingAverage(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        final long startIndex = max(0, index - length + 1);
        final long observations = index - startIndex + 1;
        final Num average = sma.getValue(index);
        Num variance = numOfZero();
        for (long indicatorIndex = startIndex; indicatorIndex <= index; indicatorIndex++) {
            variance = variance.add(indicator.getValue(indicatorIndex).subtract(average).square());
        }
        return variance.divide(unbiased ? max(1, observations - 1) : observations);
    }
}
