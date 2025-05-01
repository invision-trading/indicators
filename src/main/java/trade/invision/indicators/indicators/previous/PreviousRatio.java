package trade.invision.indicators.indicators.previous;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static trade.invision.indicators.indicators.previous.PreviousValue.previousValue;

/**
 * {@link PreviousRatio} is a {@link Num} {@link Indicator} to provide the current value divided by the
 * <i>n</i>-th previous value.
 */
public class PreviousRatio extends Indicator<Num> {

    /**
     * Calls {@link #previousRatio(Indicator, int)} with <code>n</code> set to <code>1</code>.
     */
    public static PreviousRatio previousRatio(Indicator<Num> indicator) {
        return previousRatio(indicator, 1);
    }

    /**
     * Gets a {@link PreviousRatio}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static PreviousRatio previousRatio(Indicator<Num> indicator, int n) {
        return CACHE.get(new CacheKey(indicator, n), key -> new PreviousRatio(indicator, n));
    }

    private static final Cache<CacheKey, PreviousRatio> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int n;
    }

    private final Indicator<Num> indicator;
    private final PreviousValue<Num> previousValue;

    protected PreviousRatio(Indicator<Num> indicator, int n) {
        super(indicator.getSeries(), n);
        this.indicator = indicator;
        previousValue = previousValue(indicator, n);
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).divide(previousValue.getValue(index));
    }
}
