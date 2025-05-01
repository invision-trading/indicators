package trade.invision.indicators.indicator.mad;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link MovingAverageDistanceDifference} is a {@link Num} {@link Indicator} to provide the Moving Average Distance
 * Difference (MADD) over a <code>length</code> of values.
 *
 * @see <a
 * href="https://chartschool.stockcharts.com/table-of-contents/technical-indicators-and-overlays/technical-indicators/distance-from-moving-average">StockCharts</a>
 */
public class MovingAverageDistanceDifference extends Indicator<Num> {

    /**
     * @see #movingAverageDistanceDifference(Indicator, int, MovingAverageSupplier)
     */
    public static MovingAverageDistanceDifference madd(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return movingAverageDistanceDifference(indicator, length, movingAverageSupplier);
    }

    /**
     * Gets a {@link MovingAverageDistanceDifference}.
     *
     * @param indicator             the {@link Indicator}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static MovingAverageDistanceDifference movingAverageDistanceDifference(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(indicator, length, movingAverageSupplier),
                key -> new MovingAverageDistanceDifference(indicator, length, movingAverageSupplier));
    }

    private static final Cache<CacheKey, MovingAverageDistanceDifference> CACHE =
            Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final Indicator<Num> indicator;
    private final Indicator<Num> ma;

    protected MovingAverageDistanceDifference(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        ma = movingAverageSupplier.supply(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).subtract(ma.getValue(index));
    }
}
