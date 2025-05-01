package trade.invision.indicators.indicators.rsi;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.gainloss.Gain.gain;
import static trade.invision.indicators.indicators.gainloss.Loss.loss;

/**
 * {@link RelativeStrengthIndex} is a {@link Num} {@link Indicator} to provide the Relative Strength Index (RSI) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/r/rsi.asp">Investopedia</a>
 */
public class RelativeStrengthIndex extends Indicator<Num> {

    /**
     * @see #relativeStrengthIndex(Indicator, int, MovingAverageSupplier)
     */
    public static RelativeStrengthIndex rsi(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return relativeStrengthIndex(indicator, length, movingAverageSupplier);
    }

    /**
     * Gets a {@link RelativeStrengthIndex}.
     *
     * @param indicator             the {@link Indicator}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static RelativeStrengthIndex relativeStrengthIndex(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(indicator, length, movingAverageSupplier),
                key -> new RelativeStrengthIndex(indicator, length, movingAverageSupplier));
    }

    private static final Cache<CacheKey, RelativeStrengthIndex> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final Indicator<Num> averageGain;
    private final Indicator<Num> averageLoss;

    protected RelativeStrengthIndex(Indicator<Num> indicator, int length, MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        averageGain = movingAverageSupplier.supply(gain(indicator), length);
        averageLoss = movingAverageSupplier.supply(loss(indicator), length);
    }

    @Override
    protected Num calculate(long index) {
        final Num gain = averageGain.getValue(index);
        final Num loss = averageLoss.getValue(index);
        if (loss.isZero(series.getEpsilon())) {
            return gain.isZero(series.getEpsilon()) ? numOfZero() : numOfHundred();
        }
        return numOfHundred().subtract(numOfHundred().divide(numOfOne().add(gain.divide(loss)))).ifNaN(numOfHundred());
    }
}
