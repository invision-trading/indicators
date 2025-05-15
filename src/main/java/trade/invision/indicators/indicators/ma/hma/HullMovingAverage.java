package trade.invision.indicators.indicators.ma.hma;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.wma.WeightedMovingAverage;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.rint;
import static java.lang.Math.sqrt;
import static trade.invision.indicators.indicators.constant.ConstantValue.constantValue;
import static trade.invision.indicators.indicators.ma.wma.WeightedMovingAverage.weightedMovingAverage;
import static trade.invision.indicators.indicators.operation.binary.NumBinaryOperations.multiply;
import static trade.invision.indicators.indicators.operation.binary.NumBinaryOperations.subtract;

/**
 * {@link HullMovingAverage} is a {@link Num} {@link Indicator} to provide a Hull Moving Average (HMA) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://alanhull.com/hull-moving-average">Alan Hull</a>
 */
public class HullMovingAverage extends Indicator<Num> {

    /**
     * @see #hullMovingAverage(Indicator, int)
     */
    public static HullMovingAverage hma(Indicator<Num> indicator, int length) {
        return hullMovingAverage(indicator, length);
    }

    /**
     * Gets a {@link HullMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static HullMovingAverage hullMovingAverage(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new HullMovingAverage(indicator, length));
    }

    private static final Cache<CacheKey, HullMovingAverage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final WeightedMovingAverage hma;

    protected HullMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        final WeightedMovingAverage halfWma = weightedMovingAverage(indicator, (int) rint(length / 2.0));
        final WeightedMovingAverage wma = weightedMovingAverage(indicator, length);
        final Indicator<Num> inner = subtract(multiply(constantValue(series, numOfTwo()), halfWma), wma);
        hma = weightedMovingAverage(inner, (int) rint(sqrt(length)));
    }

    @Override
    protected Num calculate(long index) {
        return hma.getValue(index);
    }
}
