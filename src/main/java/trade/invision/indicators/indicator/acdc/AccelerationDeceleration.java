package trade.invision.indicators.indicator.acdc;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.indicators.indicator.macd.MovingAverageConvergenceDivergence;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static trade.invision.indicators.indicator.macd.MovingAverageConvergenceDivergence.movingAverageConvergenceDivergence;

/**
 * {@link AccelerationDeceleration} is a {@link Num} {@link Indicator} to provide the Acceleration/Deceleration (ACDC)
 * over a <code>firstLength</code> and <code>secondLength</code> of values.
 *
 * @see <a
 * href="https://toslc.thinkorswim.com/center/reference/Tech-Indicators/studies-library/A-B/AccelerationDecelerationOsc">thinkorswim</a>
 */
public class AccelerationDeceleration extends Indicator<Num> {

    /**
     * @see #accelerationDeceleration(Indicator, int, int, MovingAverageSupplier)
     */
    public static AccelerationDeceleration acdc(Indicator<Num> indicator,
            int firstLength, int secondLength, MovingAverageSupplier movingAverageSupplier) {
        return accelerationDeceleration(indicator, firstLength, secondLength, movingAverageSupplier);
    }

    /**
     * Gets a {@link AccelerationDeceleration}.
     *
     * @param indicator             the {@link Indicator}
     * @param firstLength           the first averaging length (typically 5)
     * @param secondLength          the second averaging length (typically 34)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static AccelerationDeceleration accelerationDeceleration(Indicator<Num> indicator,
            int firstLength, int secondLength, MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(indicator, firstLength, secondLength, movingAverageSupplier),
                key -> new AccelerationDeceleration(indicator, firstLength, secondLength, movingAverageSupplier));
    }

    private static final Cache<CacheKey, AccelerationDeceleration> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int firstLength;
        int secondLength;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final MovingAverageConvergenceDivergence macd;
    private final Indicator<Num> averagingIndicator;

    protected AccelerationDeceleration(Indicator<Num> indicator, int firstLength, int secondLength,
            MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), max(firstLength, secondLength));
        checkArgument(firstLength > 0, "'firstLength' must be greater than zero!");
        checkArgument(secondLength > 0, "'secondLength' must be greater than zero!");
        macd = movingAverageConvergenceDivergence(indicator, firstLength, secondLength, movingAverageSupplier);
        averagingIndicator = movingAverageSupplier.supply(macd, firstLength);
    }

    @Override
    protected Num calculate(long index) {
        return macd.getValue(index).subtract(averagingIndicator.getValue(index));
    }
}
