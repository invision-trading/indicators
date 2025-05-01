package trade.invision.indicators.indicators.crossed;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static java.lang.Math.max;

/**
 * {@link CrossedUp} is a {@link Boolean} {@link Indicator} to provide a positive crossing signal. A positive crossing
 * occurs when <code>first</code> crosses above <code>second</code>.
 */
public class CrossedUp extends Indicator<Boolean> {

    /**
     * Gets a {@link CrossedUp}.
     *
     * @param first  the first {@link Indicator}
     * @param second the second {@link Indicator}
     */
    public static CrossedUp crossedUp(Indicator<Num> first, Indicator<Num> second) {
        return CACHE.get(new CacheKey(first, second), key -> new CrossedUp(first, second));
    }

    private static final Cache<CacheKey, CrossedUp> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> first;
        Indicator<Num> second;
    }

    private final Indicator<Num> first;
    private final Indicator<Num> second;

    protected CrossedUp(Indicator<Num> first, Indicator<Num> second) {
        super(first.getSeries(), max(first.getMinimumStableIndex(), second.getMinimumStableIndex()));
        this.first = first.caching();
        this.second = second.caching();
    }

    @Override
    protected Boolean calculate(long index) {
        if (index == 0 || first.getValue(index).isLessThanOrEqual(second.getValue(index), series.getEpsilon())) {
            return false;
        }
        do {
            index--;
        } while (index > 0 && first.getValue(index).isEqual(second.getValue(index), series.getEpsilon()));
        return first.getValue(index).isLessThan(second.getValue(index));
    }
}
