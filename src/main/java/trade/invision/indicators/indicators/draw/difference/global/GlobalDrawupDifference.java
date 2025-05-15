package trade.invision.indicators.indicators.draw.difference.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link GlobalDrawupDifference} is a {@link Num} {@link Indicator} to provide the global drawup difference. This is
 * similar to the global drawup percentage (MDU), but instead of providing a percentage, this provides a difference
 * (subtraction).
 *
 * @see <a href="https://www.investopedia.com/terms/m/maximum-drawdown-mdd.asp">Investopedia</a>
 */
public class GlobalDrawupDifference extends AbstractDrawupDrawdown {

    /**
     * Gets a {@link GlobalDrawupDifference}.
     *
     * @param indicator the {@link Indicator}
     */
    public static GlobalDrawupDifference globalDrawupDifference(Indicator<Num> indicator) {
        return CACHE.get(new CacheKey(indicator), key -> new GlobalDrawupDifference(indicator));
    }

    private static final Cache<CacheKey, GlobalDrawupDifference> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
    }

    protected GlobalDrawupDifference(Indicator<Num> indicator) {
        super(indicator, null, true, false);
    }
}
