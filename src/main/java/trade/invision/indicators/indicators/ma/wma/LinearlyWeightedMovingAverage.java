package trade.invision.indicators.indicators.ma.wma;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.min;

/**
 * {@link LinearlyWeightedMovingAverage} is a {@link Num} {@link Indicator} to provide a Linearly Weighted Moving
 * Average (LWMA) over a <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/l/linearlyweightedmovingaverage.asp">Investopedia</a>
 */
public class LinearlyWeightedMovingAverage extends Indicator<Num> {

    /**
     * @see #linearlyWeightedMovingAverage(Indicator, int)
     */
    public static LinearlyWeightedMovingAverage lwma(Indicator<Num> indicator, int length) {
        return linearlyWeightedMovingAverage(indicator, length);
    }

    /**
     * Convenience static method for {@link #LinearlyWeightedMovingAverage(Indicator, int)}.
     */
    public static LinearlyWeightedMovingAverage linearlyWeightedMovingAverage(Indicator<Num> indicator, int length) {
        return new LinearlyWeightedMovingAverage(indicator, length);
    }

    private final Indicator<Num> indicator;
    private final int length;

    /**
     * Instantiates a new {@link LinearlyWeightedMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public LinearlyWeightedMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
    }

    @Override
    protected Num calculate(long index) {
        Num weightedSum = numOfZero();
        long sumOfWeights = 0;
        final long loopLength = min(index + 1, length);
        long indicatorIndex = index;
        for (long loopIndex = loopLength; loopIndex > 0; loopIndex--) {
            weightedSum = weightedSum.add(indicator.getValue(indicatorIndex).multiply(loopIndex));
            sumOfWeights += loopIndex;
            indicatorIndex--;
        }
        return weightedSum.divide(sumOfWeights);
    }
}
