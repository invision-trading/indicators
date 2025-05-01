package trade.invision.indicators.indicators.ppo;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link PercentagePriceOscillator} is a {@link Num} {@link Indicator} to provide the Percentage Price Oscillator (PPO)
 * over a <code>shortLength</code> and <code>longLength</code> of values. This is the same as the Range Action
 * Verification Index (RAVI).
 *
 * @see <a href="https://www.investopedia.com/terms/p/ppo.asp">Investopedia</a>
 */
public class PercentagePriceOscillator extends Indicator<Num> {

    /**
     * @see #percentagePriceOscillator(Indicator, int, int, MovingAverageSupplier)
     */
    public static PercentagePriceOscillator ppo(Indicator<Num> indicator,
            int shortLength, int longLength, MovingAverageSupplier movingAverageSupplier) {
        return percentagePriceOscillator(indicator, shortLength, longLength, movingAverageSupplier);
    }

    /**
     * Gets a {@link PercentagePriceOscillator}.
     *
     * @param indicator             the {@link Indicator}
     * @param shortLength           the short averaging length (typically 12)
     * @param longLength            the long averaging length (typically 26)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static PercentagePriceOscillator percentagePriceOscillator(Indicator<Num> indicator,
            int shortLength, int longLength, MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(indicator, shortLength, longLength, movingAverageSupplier),
                key -> new PercentagePriceOscillator(indicator, shortLength, longLength, movingAverageSupplier));
    }

    private static final Cache<CacheKey, PercentagePriceOscillator> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int shortLength;
        int longLength;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final Indicator<Num> shortAverage;
    private final Indicator<Num> longAverage;

    protected PercentagePriceOscillator(Indicator<Num> indicator, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), max(shortLength, longLength));
        checkArgument(shortLength > 0, "'shortLength' must be greater than zero!");
        checkArgument(longLength > 0, "'longLength' must be greater than zero!");
        shortAverage = movingAverageSupplier.supply(indicator, shortLength);
        longAverage = movingAverageSupplier.supply(indicator, longLength);
    }

    @Override
    protected Num calculate(long index) {
        return shortAverage.getValue(index).divide(longAverage.getValue(index)).decrement().multiply(numOfHundred());
    }
}
