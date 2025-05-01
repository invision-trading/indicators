package trade.invision.indicators.indicator.draw.percentage.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link GlobalDrawdownPercentage} is a {@link Num} {@link Indicator} to provide the global drawdown percentage. This
 * is also known as the maximum drawdown (MDD). The percentage is represented as a fractional. For example, a provided
 * value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/m/maximum-drawdown-mdd.asp">Investopedia</a>
 */
public class GlobalDrawdownPercentage extends AbstractDrawupDrawdown {

    /**
     * @see #globalDrawdownPercentage(Indicator)
     */
    public static GlobalDrawdownPercentage mdd(Indicator<Num> indicator) {
        return globalDrawdownPercentage(indicator);
    }

    /**
     * Gets a {@link GlobalDrawdownPercentage}.
     *
     * @param indicator the {@link Indicator}
     */
    public static GlobalDrawdownPercentage globalDrawdownPercentage(Indicator<Num> indicator) {
        return CACHE.get(new CacheKey(indicator), key -> new GlobalDrawdownPercentage(indicator));
    }

    private static final Cache<CacheKey, GlobalDrawdownPercentage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
    }

    protected GlobalDrawdownPercentage(Indicator<Num> indicator) {
        super(indicator, null, false, true);
    }
}
