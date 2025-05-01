package trade.invision.indicators.indicator.draw.difference.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link LocalDrawupDifference} is a {@link Num} {@link Indicator} to provide the local drawup difference. This is
 * similar to the local drawup percentage (MDD), but instead of providing a percentage, this provides a difference
 * (subtraction).
 *
 * @see <a href="https://www.investopedia.com/terms/d/drawdown.asp">Investopedia</a>
 */
public class LocalDrawupDifference extends AbstractDrawupDrawdown {

    /**
     * Gets a {@link LocalDrawupDifference}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static LocalDrawupDifference localDrawupDifference(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new LocalDrawupDifference(indicator, length));
    }

    private static final Cache<CacheKey, LocalDrawupDifference> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    protected LocalDrawupDifference(Indicator<Num> indicator, int length) {
        super(indicator, length, true, false);
    }
}
