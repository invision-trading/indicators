package trade.invision.indicators.indicators.ma.wma;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.min;

/**
 * {@link WeightedMovingAverage} is a {@link Num} {@link Indicator} to provide a Weighted Moving Average (WMA) over a
 * <code>length</code> of values. This is similar to Linearly Weighted Moving Average (LWMA), but uses a divisor of
 * <code>(length * (length + 1.0)) / 2.0</code>.
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=WeightedMA.htm">FM Labs</a>
 */
public class WeightedMovingAverage extends Indicator<Num> {

    /**
     * @see #weightedMovingAverage(Indicator, int)
     */
    public static WeightedMovingAverage wma(Indicator<Num> indicator, int length) {
        return weightedMovingAverage(indicator, length);
    }

    /**
     * Convenience static method for {@link #WeightedMovingAverage(Indicator, int)}.
     */
    public static WeightedMovingAverage weightedMovingAverage(Indicator<Num> indicator, int length) {
        return new WeightedMovingAverage(indicator, length);
    }

    private final Indicator<Num> indicator;
    private final int length;

    /**
     * Instantiates a new {@link WeightedMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public WeightedMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
    }

    @Override
    protected Num calculate(long index) {
        Num weightedSum = numOfZero();
        final long loopLength = min(index + 1, length);
        long indicatorIndex = index;
        for (long loopIndex = loopLength; loopIndex > 0; loopIndex--) {
            weightedSum = weightedSum.add(indicator.getValue(indicatorIndex).multiply(loopIndex));
            indicatorIndex--;
        }
        return weightedSum.divide(numOf(loopLength * (loopLength + 1.0)).divide(numOfTwo()));
    }
}
