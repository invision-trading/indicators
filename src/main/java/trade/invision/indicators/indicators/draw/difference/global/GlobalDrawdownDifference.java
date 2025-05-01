package trade.invision.indicators.indicators.draw.difference.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link GlobalDrawdownDifference} is a {@link Num} {@link Indicator} to provide the global drawdown difference. This
 * is similar to the global drawdown percentage, but instead of providing a percentage, this provides a difference
 * (subtraction).
 *
 * @see <a href="https://www.investopedia.com/terms/m/maximum-drawdown-mdd.asp">Investopedia</a>
 */
public class GlobalDrawdownDifference extends AbstractDrawupDrawdown {

    /**
     * Gets a {@link GlobalDrawdownDifference}.
     *
     * @param indicator the {@link Indicator}
     */
    public static GlobalDrawdownDifference globalDrawdownDifference(Indicator<Num> indicator) {
        return CACHE.get(new CacheKey(indicator), key -> new GlobalDrawdownDifference(indicator));
    }

    private static final Cache<CacheKey, GlobalDrawdownDifference> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
    }

    protected GlobalDrawdownDifference(Indicator<Num> indicator) {
        super(indicator, null, false, false);
    }
}
