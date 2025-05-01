package trade.invision.indicators.indicator.previous;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

/**
 * {@link PreviousPercentChange} is a {@link Num} {@link Indicator} to provide the percent change of the current value
 * from the <i>n</i>-th previous value. This is also referred to as Rate of Change (ROC) or Momentum. The percentage is
 * represented as a fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 */
public class PreviousPercentChange extends PreviousRatio {

    /**
     * Calls {@link #previousPercentChange(Indicator, int)} with <code>n</code> set to <code>1</code>.
     */
    public static PreviousPercentChange previousPercentChange(Indicator<Num> indicator) {
        return previousPercentChange(indicator, 1);
    }

    /**
     * Gets a {@link PreviousPercentChange}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static PreviousPercentChange previousPercentChange(Indicator<Num> indicator, int n) {
        return CACHE.get(new CacheKey(indicator, n), key -> new PreviousPercentChange(indicator, n));
    }

    private static final Cache<CacheKey, PreviousPercentChange> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int n;
    }

    protected PreviousPercentChange(Indicator<Num> indicator, int n) {
        super(indicator, n);
    }

    @Override
    protected Num calculate(long index) {
        return super.calculate(index).decrement();
    }
}
