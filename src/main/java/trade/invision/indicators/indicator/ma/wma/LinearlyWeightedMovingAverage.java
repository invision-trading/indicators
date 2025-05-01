package trade.invision.indicators.indicator.ma.wma;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.min;

/**
 * {@link LinearlyWeightedMovingAverage} is a {@link Num} {@link Indicator} to provide a Linearly Weighted Moving
 * Average (LWMA) over a <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/l/linearlyweightedmovingaverage.asp">Investopedia</a>
 */
public class LinearlyWeightedMovingAverage extends Indicator<Num> {

    /**
     * @see #linearlyWeightedMovingAverage(Indicator, int)
     */
    public static LinearlyWeightedMovingAverage lwma(Indicator<Num> indicator, int length) {
        return linearlyWeightedMovingAverage(indicator, length);
    }

    /**
     * Gets a {@link LinearlyWeightedMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static LinearlyWeightedMovingAverage linearlyWeightedMovingAverage(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new LinearlyWeightedMovingAverage(indicator, length));
    }

    private static final Cache<CacheKey, LinearlyWeightedMovingAverage> CACHE =
            Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final Indicator<Num> indicator;
    private final int length;

    protected LinearlyWeightedMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
    }

    @Override
    protected Num calculate(long index) {
        Num weightedSum = numOfZero();
        long sumOfWeights = 0;
        final long loopLength = min(index + 1, length);
        long indicatorIndex = index;
        for (long loopIndex = loopLength; loopIndex > 0; loopIndex--) {
            weightedSum = weightedSum.add(indicator.getValue(indicatorIndex).multiply(loopIndex));
            sumOfWeights += loopIndex;
            indicatorIndex--;
        }
        return weightedSum.divide(sumOfWeights);
    }
}
