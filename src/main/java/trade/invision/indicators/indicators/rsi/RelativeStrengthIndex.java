package trade.invision.indicators.indicators.rsi;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.gainloss.Gain;
import trade.invision.indicators.indicators.gainloss.Loss;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

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
     * Convenience static method for {@link #RelativeStrengthIndex(Indicator, int, MovingAverageSupplier)}.
     */
    public static RelativeStrengthIndex relativeStrengthIndex(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return new RelativeStrengthIndex(indicator, length, movingAverageSupplier);
    }

    private final Indicator<Num> averageGain;
    private final Indicator<Num> averageLoss;

    /**
     * Instantiates a new {@link RelativeStrengthIndex}.
     *
     * @param indicator             the {@link Indicator}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public RelativeStrengthIndex(Indicator<Num> indicator, int length, MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        averageGain = movingAverageSupplier.supply(new Gain(indicator), length);
        averageLoss = movingAverageSupplier.supply(new Loss(indicator), length);
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
