package trade.invision.indicators.indicators.draw.difference.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link LocalDrawdownDifference} is a {@link Num} {@link Indicator} to provide the local drawdown difference. This is
 * similar to the local drawdown percentage, but instead of providing a percentage, this provides a difference
 * (subtraction).
 *
 * @see <a href="https://www.investopedia.com/terms/d/drawdown.asp">Investopedia</a>
 */
public class LocalDrawdownDifference extends AbstractDrawupDrawdown {

    /**
     * Gets a {@link LocalDrawdownDifference}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static LocalDrawdownDifference localDrawdownDifference(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new LocalDrawdownDifference(indicator, length));
    }

    private static final Cache<CacheKey, LocalDrawdownDifference> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    protected LocalDrawdownDifference(Indicator<Num> indicator, int length) {
        super(indicator, length, false, false);
    }
}
