package trade.invision.indicators.indicators.risingfalling.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link LocalFallingPercentage} is a {@link Num} {@link Indicator} to provide the percentage of falling values over a
 * <code>length</code> of values. A falling value is defined as a value being less than its previous value. The
 * percentage is represented as a fractional. For example, a provided value of <code>0.15</code> would represent
 * <code>15%</code>.
 */
public class LocalFallingPercentage extends AbstractLocalRisingFallingPercentage {

    /**
     * Gets a {@link LocalFallingPercentage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static LocalFallingPercentage localFallingPercentage(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new LocalFallingPercentage(indicator, length));
    }

    private static final Cache<CacheKey, LocalFallingPercentage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    protected LocalFallingPercentage(Indicator<Num> indicator, int length) {
        super(indicator, length, false);
    }
}
