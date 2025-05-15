package trade.invision.indicators.indicators.ma.ema;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.ma.ema.ExponentialMovingAverage.exponentialMovingAverage;

/**
 * {@link TripleExponentialMovingAverage} is a {@link Num} {@link Indicator} to provide a Triple Exponential Moving
 * Average (TEMA) over a <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/t/triple-exponential-moving-average.asp">Investopedia</a>
 */
public class TripleExponentialMovingAverage extends Indicator<Num> {

    /**
     * @see #tripleExponentialMovingAverage(Indicator, int)
     */
    public static TripleExponentialMovingAverage tema(Indicator<Num> indicator, int length) {
        return tripleExponentialMovingAverage(indicator, length);
    }

    /**
     * Calls {@link #tripleExponentialMovingAverage(Indicator, int, Num)} with <code>smoothing</code> set to
     * <code>2</code>.
     */
    public static TripleExponentialMovingAverage tripleExponentialMovingAverage(Indicator<Num> indicator, int length) {
        return tripleExponentialMovingAverage(indicator, length, indicator.getSeries().getNumFactory().two());
    }

    /**
     * @see #tripleExponentialMovingAverage(Indicator, int, Num)
     */
    public static TripleExponentialMovingAverage tema(Indicator<Num> indicator, int length, Num smoothing) {
        return tripleExponentialMovingAverage(indicator, length, smoothing);
    }

    /**
     * Gets a {@link TripleExponentialMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param smoothing the smoothing factor (typically 2)
     */
    public static TripleExponentialMovingAverage tripleExponentialMovingAverage(Indicator<Num> indicator, int length,
            Num smoothing) {
        return CACHE.get(new CacheKey(indicator, length, smoothing),
                key -> new TripleExponentialMovingAverage(indicator, length, smoothing));
    }

    private static final Cache<CacheKey, TripleExponentialMovingAverage> CACHE =
            Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
        Num smoothing;
    }

    private final ExponentialMovingAverage ema;
    private final ExponentialMovingAverage emaOfEma;
    private final ExponentialMovingAverage emaOfEmaOfEma;

    protected TripleExponentialMovingAverage(Indicator<Num> indicator, int length, Num smoothing) {
        super(indicator.getSeries(), length * 3);
        checkArgument(length > 0, "'length' must be greater than zero!");
        ema = exponentialMovingAverage(indicator, length, smoothing);
        emaOfEma = exponentialMovingAverage(ema, length, smoothing);
        emaOfEmaOfEma = exponentialMovingAverage(emaOfEma, length, smoothing);
    }

    @Override
    protected Num calculate(long index) {
        return numOfThree().multiply(ema.getValue(index).subtract(emaOfEma.getValue(index)))
                .add(emaOfEmaOfEma.getValue(index));
    }
}
