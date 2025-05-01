package trade.invision.indicators.indicators.cumulative;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link CumulativeSum} is a {@link Num} {@link Indicator} to provide the cumulative sum (summation) over a
 * <code>length</code> of values. This is also known as a running total. In mathematical notation, this is denoted by a
 * capital sigma.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Summation">Wikipedia</a>
 */
public class CumulativeSum extends Indicator<Num> {

    /**
     * @see #cumulativeSum(Indicator, int)
     */
    public static CumulativeSum summation(Indicator<Num> indicator, int length) {
        return cumulativeSum(indicator, length);
    }

    /**
     * Gets a {@link CumulativeSum}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param length    the number of values to sum over
     */
    public static CumulativeSum cumulativeSum(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new CumulativeSum(indicator, length));
    }

    private static final Cache<CacheKey, CumulativeSum> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final Indicator<Num> indicator;
    private final int length;
    private long previousIndex;
    private Num previousValue;
    private Num previousSum;

    protected CumulativeSum(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
        previousIndex = -1;
        previousValue = numOfZero();
        previousSum = numOfZero();
    }

    @Override
    protected Num calculate(long index) {
        final Num currentValue = indicator.getValue(index);
        Num sum;
        // Use optimized calculation for identical 'index' or consecutive 'index'.
        if (index == previousIndex) {
            sum = previousSum.subtract(previousValue).add(currentValue);
        } else if (index == previousIndex + 1) {
            sum = previousSum.add(currentValue);
            if (index >= length) {
                sum = sum.subtract(indicator.getValue(index - length));
            }
        } else {
            sum = numOfZero();
            for (long sumIndex = max(0, index - length + 1); sumIndex < index; sumIndex++) {
                sum = sum.add(indicator.getValue(sumIndex));
            }
            sum = sum.add(currentValue);
        }
        previousIndex = index;
        previousValue = currentValue;
        previousSum = sum;
        return sum;
    }
}
