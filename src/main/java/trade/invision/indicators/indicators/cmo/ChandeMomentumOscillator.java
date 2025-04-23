package trade.invision.indicators.indicators.cmo;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.cumulative.CumulativeSum;
import trade.invision.indicators.indicators.gainloss.Gain;
import trade.invision.indicators.indicators.gainloss.Loss;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

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
     * Convenience static method for {@link #ChandeMomentumOscillator(Indicator, int)}.
     */
    public static ChandeMomentumOscillator chandeMomentumOscillator(Indicator<Num> indicator, int length) {
        return new ChandeMomentumOscillator(indicator, length);
    }

    private final Indicator<Num> cumulativeGain;
    private final Indicator<Num> cumulativeLoss;

    /**
     * Instantiates a new {@link ChandeMomentumOscillator}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public ChandeMomentumOscillator(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        cumulativeGain = new CumulativeSum(new Gain(indicator), length);
        cumulativeLoss = new CumulativeSum(new Loss(indicator), length);
    }

    @Override
    protected Num calculate(long index) {
        final Num cumulativeGainValue = cumulativeGain.getValue(index);
        final Num cumulativeLossValue = cumulativeLoss.getValue(index);
        return cumulativeGainValue.subtract(cumulativeLossValue).divide(cumulativeGainValue.add(cumulativeLossValue))
                .multiply(numOfHundred());
    }
}
