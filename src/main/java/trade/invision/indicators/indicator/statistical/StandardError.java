package trade.invision.indicators.indicator.statistical;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static trade.invision.indicators.indicator.statistical.StandardDeviation.standardDeviation;

/**
 * {@link StandardError} is a {@link Num} {@link Indicator} to provide the statistical standard error (stderr) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Standard_error">Wikipedia</a>
 */
public class StandardError extends Indicator<Num> {

    /**
     * @see #standardError(Indicator, int, boolean)
     */
    public static StandardError stderr(Indicator<Num> indicator, int length, boolean unbiased) {
        return standardError(indicator, length, unbiased);
    }

    /**
     * Gets a {@link StandardError}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param unbiased  <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the
     *                  standard error calculation, <code>false</code> to use <code>n</code> (biased)
     */
    public static StandardError standardError(Indicator<Num> indicator, int length, boolean unbiased) {
        return CACHE.get(new CacheKey(indicator, length, unbiased),
                key -> new StandardError(indicator, length, unbiased));
    }

    private static final Cache<CacheKey, StandardError> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
        boolean unbiased;
    }

    private final int length;
    private final StandardDeviation standardDeviation;

    protected StandardError(Indicator<Num> indicator, int length, boolean unbiased) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.length = length;
        standardDeviation = standardDeviation(indicator, length, unbiased);
    }

    @Override
    protected Num calculate(long index) {
        final long startIndex = max(0, index - length + 1);
        final long observations = index - startIndex + 1;
        return standardDeviation.getValue(index).divide(numOf(observations).squareRoot());
    }
}
