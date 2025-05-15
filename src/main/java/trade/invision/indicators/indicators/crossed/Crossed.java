package trade.invision.indicators.indicators.crossed;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static java.lang.Math.max;
import static trade.invision.indicators.indicators.crossed.CrossedDown.crossedDown;
import static trade.invision.indicators.indicators.crossed.CrossedUp.crossedUp;

/**
 * {@link Crossed} is a {@link Boolean} {@link Indicator} to provide a positive or negative crossing signal. A crossing
 * occurs when <code>first</code> crosses above or below <code>second</code>.
 */
public class Crossed extends Indicator<Boolean> {

    /**
     * Gets a {@link Crossed}.
     *
     * @param first  the first {@link Indicator}
     * @param second the second {@link Indicator}
     */
    public static Crossed crossed(Indicator<Num> first, Indicator<Num> second) {
        return CACHE.get(new CacheKey(first, second), key -> new Crossed(first, second));
    }

    private static final Cache<CacheKey, Crossed> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> first;
        Indicator<Num> second;
    }

    private final CrossedUp crossedUp;
    private final CrossedDown crossedDown;

    protected Crossed(Indicator<Num> first, Indicator<Num> second) {
        super(first.getSeries(), max(first.getMinimumStableIndex(), second.getMinimumStableIndex()));
        crossedUp = crossedUp(first, second);
        crossedDown = crossedDown(first, second);
    }

    @Override
    protected Boolean calculate(long index) {
        return crossedUp.getValue(index) || crossedDown.getValue(index);
    }
}
