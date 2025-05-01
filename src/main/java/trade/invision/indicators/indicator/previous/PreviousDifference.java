package trade.invision.indicators.indicator.previous;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.previous.PreviousValue.previousValue;

/**
 * {@link PreviousDifference} is a {@link Num} {@link Indicator} to provide the current value subtracted by the
 * <i>n</i>-th previous value.
 */
public class PreviousDifference extends Indicator<Num> {

    /**
     * Calls {@link #previousDifference(Indicator, int)} with <code>n</code> set to <code>1</code>.
     */
    public static PreviousDifference previousDifference(Indicator<Num> indicator) {
        return previousDifference(indicator, 1);
    }

    /**
     * Gets a {@link PreviousDifference}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static PreviousDifference previousDifference(Indicator<Num> indicator, int n) {
        return CACHE.get(new CacheKey(indicator, n), key -> new PreviousDifference(indicator, n));
    }

    private static final Cache<CacheKey, PreviousDifference> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int n;
    }

    private final Indicator<Num> indicator;
    private final PreviousValue<Num> previousValue;

    protected PreviousDifference(Indicator<Num> indicator, int n) {
        super(indicator.getSeries(), n);
        this.indicator = indicator;
        previousValue = previousValue(indicator, n);
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).subtract(previousValue.getValue(index));
    }
}
