package trade.invision.indicators.indicators.rsi;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.gainloss.Gain;
import trade.invision.indicators.indicators.gainloss.Loss;
import trade.invision.num.Num;

import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link RelativeStrengthIndex} is a {@link Num} {@link Indicator} to provide the Relative Strength Index (RSI) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/r/rsi.asp">Investopedia</a>
 */
public class RelativeStrengthIndex extends Indicator<Num> {

    /**
     * @see #relativeStrengthIndex(Indicator, int, BiFunction)
     */
    public static RelativeStrengthIndex rsi(Indicator<Num> indicator, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return relativeStrengthIndex(indicator, length, averagingIndicatorSupplier);
    }

    /**
     * Convenience static method for {@link #RelativeStrengthIndex(Indicator, int, BiFunction)}.
     */
    public static RelativeStrengthIndex relativeStrengthIndex(Indicator<Num> indicator, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return new RelativeStrengthIndex(indicator, length, averagingIndicatorSupplier);
    }

    private final Indicator<Num> averageGain;
    private final Indicator<Num> averageLoss;

    /**
     * Instantiates a new {@link RelativeStrengthIndex}.
     *
     * @param indicator                  the {@link Indicator}
     * @param length                     the number of values to look back at
     * @param averagingIndicatorSupplier the {@link BiFunction} to supply the averaging {@link Indicator}
     */
    public RelativeStrengthIndex(Indicator<Num> indicator, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        averageGain = averagingIndicatorSupplier.apply(new Gain(indicator), length);
        averageLoss = averagingIndicatorSupplier.apply(new Loss(indicator), length);
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
