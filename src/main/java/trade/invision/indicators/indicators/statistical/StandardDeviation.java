package trade.invision.indicators.indicators.statistical;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.statistical.Variance.variance;

/**
 * {@link StandardDeviation} is a {@link Num} {@link Indicator} to provide the statistical standard deviation (stddev)
 * over a <code>length</code> of values. The calculation is the same as the root-mean-square deviation (RMSD).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Standard_deviation">Wikipedia</a>
 */
public class StandardDeviation extends Indicator<Num> {

    /**
     * @see #standardDeviation(Indicator, int, boolean)
     */
    public static StandardDeviation stddev(Indicator<Num> indicator, int length, boolean unbiased) {
        return standardDeviation(indicator, length, unbiased);
    }

    /**
     * Gets a {@link StandardDeviation}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param unbiased  <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the standard
     *                  deviation calculation, <code>false</code> to use <code>n</code> (biased)
     */
    public static StandardDeviation standardDeviation(Indicator<Num> indicator, int length, boolean unbiased) {
        return CACHE.get(new CacheKey(indicator, length, unbiased),
                key -> new StandardDeviation(indicator, length, unbiased));
    }

    private static final Cache<CacheKey, StandardDeviation> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
        boolean unbiased;
    }

    private final Variance variance;

    protected StandardDeviation(Indicator<Num> indicator, int length, boolean unbiased) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        variance = variance(indicator, length, unbiased);
    }

    @Override
    protected Num calculate(long index) {
        return variance.getValue(index).squareRoot();
    }
}
