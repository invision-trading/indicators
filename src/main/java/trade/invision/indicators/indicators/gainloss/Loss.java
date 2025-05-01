package trade.invision.indicators.indicators.gainloss;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.previous.PreviousDifference;
import trade.invision.num.Num;

/**
 * {@link Loss} is a {@link Num} {@link Indicator} to provide the loss of the current value compared to the
 * <i>n</i>-th previous value. This will always return a positive number or zero.
 */
public class Loss extends PreviousDifference {

    /**
     * Calls {@link #loss(Indicator, int)} with <code>n</code> set to <code>1</code>.
     */
    public static Loss loss(Indicator<Num> indicator) {
        return loss(indicator, 1);
    }

    /**
     * Gets a {@link Loss}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static Loss loss(Indicator<Num> indicator, int n) {
        return CACHE.get(new CacheKey(indicator, n), key -> new Loss(indicator, n));
    }

    private static final Cache<CacheKey, Loss> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int n;
    }

    protected Loss(Indicator<Num> indicator, int n) {
        super(indicator, n);
    }

    @Override
    protected Num calculate(long index) {
        return numOfZero().minimum(super.calculate(index)).negate();
    }
}
