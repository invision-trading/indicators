package trade.invision.indicators.indicator.draw.percentage.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link LocalDrawdownPercentage} is a {@link Num} {@link Indicator} to provide the local drawdown percentage. This is
 * also known as the drawdown (DD). The percentage is represented as a fractional. For example, a provided value of
 * <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/d/drawdown.asp">Investopedia</a>
 */
public class LocalDrawdownPercentage extends AbstractDrawupDrawdown {

    /**
     * @see #localDrawdownPercentage(Indicator, int)
     */
    public static LocalDrawdownPercentage dd(Indicator<Num> indicator, int length) {
        return localDrawdownPercentage(indicator, length);
    }

    /**
     * Gets a {@link LocalDrawdownPercentage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static LocalDrawdownPercentage localDrawdownPercentage(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new LocalDrawdownPercentage(indicator, length));
    }

    private static final Cache<CacheKey, LocalDrawdownPercentage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    protected LocalDrawdownPercentage(Indicator<Num> indicator, int length) {
        super(indicator, length, false, true);
    }
}
