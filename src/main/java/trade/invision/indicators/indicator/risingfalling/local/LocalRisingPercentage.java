package trade.invision.indicators.indicator.risingfalling.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

/**
 * {@link LocalRisingPercentage} is a {@link Num} {@link Indicator} to provide the percentage of rising values over a
 * <code>length</code> of values. A rising value is defined as a value being greater than its previous value. The
 * percentage is represented as a fractional. For example, a provided value of <code>0.15</code> would represent
 * <code>15%</code>.
 */
public class LocalRisingPercentage extends AbstractLocalRisingFallingPercentage {

    /**
     * Gets a {@link LocalRisingPercentage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static LocalRisingPercentage localRisingPercentage(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new LocalRisingPercentage(indicator, length));
    }

    private static final Cache<CacheKey, LocalRisingPercentage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    protected LocalRisingPercentage(Indicator<Num> indicator, int length) {
        super(indicator, length, true);
    }
}
