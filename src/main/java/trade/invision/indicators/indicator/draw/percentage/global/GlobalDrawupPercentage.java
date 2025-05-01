package trade.invision.indicators.indicator.draw.percentage.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link GlobalDrawupPercentage} is a {@link Num} {@link Indicator} to provide the global drawup percentage. This is
 * also known as the maximum drawup (MDU). The percentage is represented as a fractional. For example, a provided value
 * of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/m/maximum-drawdown-mdd.asp">Investopedia</a>
 */
public class GlobalDrawupPercentage extends AbstractDrawupDrawdown {

    /**
     * @see #globalDrawupPercentage(Indicator)
     */
    public static GlobalDrawupPercentage mdu(Indicator<Num> indicator) {
        return globalDrawupPercentage(indicator);
    }

    /**
     * Gets a {@link GlobalDrawupPercentage}.
     *
     * @param indicator the {@link Indicator}
     */
    public static GlobalDrawupPercentage globalDrawupPercentage(Indicator<Num> indicator) {
        return CACHE.get(new CacheKey(indicator), key -> new GlobalDrawupPercentage(indicator));
    }

    private static final Cache<CacheKey, GlobalDrawupPercentage> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
    }

    protected GlobalDrawupPercentage(Indicator<Num> indicator) {
        super(indicator, null, true, true);
    }
}
