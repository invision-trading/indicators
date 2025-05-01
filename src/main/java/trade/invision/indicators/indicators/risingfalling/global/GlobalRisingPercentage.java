package trade.invision.indicators.indicators.risingfalling.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link GlobalRisingPercentage} is a {@link Num} {@link Indicator} to provide the percentage of rising values over all
 * values. A rising value is defined as a value being greater than its previous value. The percentage is represented as
 * a fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 */
public class GlobalRisingPercentage extends AbstractGlobalRisingFallingPercentage {

    /**
     * Gets a {@link GlobalRisingPercentage}.
     *
     * @param indicator the {@link Indicator}
     */
    public static GlobalRisingPercentage globalRisingPercentage(Indicator<Num> indicator) {
        return CACHE.get(new CacheKey(indicator), key -> new GlobalRisingPercentage(indicator));
    }

    private static final Cache<CacheKey, GlobalRisingPercentage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
    }

    protected GlobalRisingPercentage(Indicator<Num> indicator) {
        super(indicator, true);
    }
}
