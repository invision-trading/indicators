package trade.invision.indicators.indicators.cmo;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.cumulative.CumulativeSum.cumulativeSum;
import static trade.invision.indicators.indicators.gainloss.Gain.gain;
import static trade.invision.indicators.indicators.gainloss.Loss.loss;

/**
 * {@link ChandeMomentumOscillator} is a {@link Num} {@link Indicator} to provide the Chande Momentum Oscillator (CMO)
 * over a <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/c/chandemomentumoscillator.asp">Investopedia</a>
 */
public class ChandeMomentumOscillator extends Indicator<Num> {

    /**
     * @see #chandeMomentumOscillator(Indicator, int)
     */
    public static ChandeMomentumOscillator cmo(Indicator<Num> indicator, int length) {
        return chandeMomentumOscillator(indicator, length);
    }

    /**
     * Gets a {@link ChandeMomentumOscillator}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static ChandeMomentumOscillator chandeMomentumOscillator(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new ChandeMomentumOscillator(indicator, length));
    }

    private static final Cache<CacheKey, ChandeMomentumOscillator> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final Indicator<Num> cumulativeGain;
    private final Indicator<Num> cumulativeLoss;

    protected ChandeMomentumOscillator(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        cumulativeGain = cumulativeSum(gain(indicator), length);
        cumulativeLoss = cumulativeSum(loss(indicator), length);
    }

    @Override
    protected Num calculate(long index) {
        final Num cumulativeGainValue = cumulativeGain.getValue(index);
        final Num cumulativeLossValue = cumulativeLoss.getValue(index);
        return cumulativeGainValue.subtract(cumulativeLossValue).divide(cumulativeGainValue.add(cumulativeLossValue))
                .multiply(numOfHundred());
    }
}
