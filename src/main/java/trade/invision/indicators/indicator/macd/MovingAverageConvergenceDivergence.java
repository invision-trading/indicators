package trade.invision.indicators.indicator.macd;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link MovingAverageConvergenceDivergence} is a {@link Num} {@link Indicator} to provide the Moving Average
 * Convergence/Divergence (MACD) over a <code>shortLength</code> and <code>longLength</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/m/macd.asp">Investopedia</a>
 */
public class MovingAverageConvergenceDivergence extends Indicator<Num> {

    /**
     * @see #movingAverageConvergenceDivergence(Indicator, int, int, MovingAverageSupplier)
     */
    public static MovingAverageConvergenceDivergence macd(Indicator<Num> indicator, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        return movingAverageConvergenceDivergence(indicator, shortLength, longLength, movingAverageSupplier);
    }

    /**
     * Gets a {@link MovingAverageConvergenceDivergence}.
     *
     * @param indicator             the {@link Indicator}
     * @param shortLength           the short averaging length (typically 12)
     * @param longLength            the long averaging length (typically 26)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static MovingAverageConvergenceDivergence movingAverageConvergenceDivergence(Indicator<Num> indicator,
            int shortLength, int longLength, MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(indicator, shortLength, longLength, movingAverageSupplier), key ->
                new MovingAverageConvergenceDivergence(indicator, shortLength, longLength, movingAverageSupplier));
    }

    private static final Cache<CacheKey, MovingAverageConvergenceDivergence> CACHE =
            Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int shortLength;
        int longLength;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final Indicator<Num> shortMa;
    private final Indicator<Num> longMa;

    protected MovingAverageConvergenceDivergence(Indicator<Num> indicator, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), max(shortLength, longLength));
        checkArgument(shortLength > 0, "'shortLength' must be greater than zero!");
        checkArgument(longLength > 0, "'longLength' must be greater than zero!");
        shortMa = movingAverageSupplier.supply(indicator, shortLength);
        longMa = movingAverageSupplier.supply(indicator, longLength);
    }

    @Override
    protected Num calculate(long index) {
        return shortMa.getValue(index).subtract(longMa.getValue(index));
    }
}
