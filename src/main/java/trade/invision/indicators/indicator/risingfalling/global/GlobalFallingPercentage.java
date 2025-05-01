package trade.invision.indicators.indicator.risingfalling.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

/**
 * {@link GlobalFallingPercentage} is a {@link Num} {@link Indicator} to provide the percentage of falling values over
 * all values. A falling value is defined as a value being less than its previous value. The percentage is represented
 * as a fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 */
public class GlobalFallingPercentage extends AbstractGlobalRisingFallingPercentage {

    /**
     * Gets a {@link GlobalFallingPercentage}.
     *
     * @param indicator the {@link Indicator}
     */
    public static GlobalFallingPercentage globalFallingPercentage(Indicator<Num> indicator) {
        return CACHE.get(new CacheKey(indicator), key -> new GlobalFallingPercentage(indicator));
    }

    private static final Cache<CacheKey, GlobalFallingPercentage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
    }

    protected GlobalFallingPercentage(Indicator<Num> indicator) {
        super(indicator, false);
    }
}
