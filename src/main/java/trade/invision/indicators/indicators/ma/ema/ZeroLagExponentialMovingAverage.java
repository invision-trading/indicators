package trade.invision.indicators.indicators.ma.ema;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.RecursiveIndicator;
import trade.invision.indicators.indicators.ma.sma.SimpleMovingAverage;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.rint;
import static trade.invision.indicators.indicators.ma.sma.SimpleMovingAverage.simpleMovingAverage;

/**
 * {@link ZeroLagExponentialMovingAverage} is a {@link Num} {@link Indicator} to provide a Zero-Lag Exponential Moving
 * Average (ZLEMA) over a <code>length</code> of values.
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=ZeroLagExpMA.htm">FM Labs</a>
 */
public class ZeroLagExponentialMovingAverage extends RecursiveIndicator<Num> {

    /**
     * @see #zeroLagExponentialMovingAverage(Indicator, int)
     */
    public static ZeroLagExponentialMovingAverage zlema(Indicator<Num> indicator, int length) {
        return zeroLagExponentialMovingAverage(indicator, length);
    }

    /**
     * Gets a {@link ZeroLagExponentialMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static ZeroLagExponentialMovingAverage zeroLagExponentialMovingAverage(Indicator<Num> indicator,
            int length) {
        return CACHE.get(new CacheKey(indicator, length),
                key -> new ZeroLagExponentialMovingAverage(indicator, length));
    }

    private static final Cache<CacheKey, ZeroLagExponentialMovingAverage> CACHE =
            Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final Indicator<Num> indicator;
    private final int length;
    private final Num k;
    private final int lag;
    private final SimpleMovingAverage initialSma;

    protected ZeroLagExponentialMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
        k = numOfTwo().divide(length + 1);
        lag = (int) rint((length - 1.0) / 2.0);
        initialSma = simpleMovingAverage(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return indicator.getValue(0);
        }
        if (index + 1 < length) {
            return initialSma.getValue(index);
        }
        final Num previousValue = getValue(index - 1);
        return k.multiply(numOfTwo().multiply(indicator.getValue(index)).subtract(indicator.getValue(index - lag)))
                .add(numOfOne().subtract(k).multiply(previousValue));
    }
}
