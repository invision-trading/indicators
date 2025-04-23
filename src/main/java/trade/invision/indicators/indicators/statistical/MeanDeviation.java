package trade.invision.indicators.indicators.statistical;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.sma.SimpleMovingAverage;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link MeanDeviation} is a {@link Num} {@link Indicator} to provide the statistical mean deviation (MD) over a
 * <code>length</code> of values. This is also known as the average absolute deviation.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Average_absolute_deviation">Wikipedia</a>
 */
public class MeanDeviation extends Indicator<Num> {

    /**
     * @see #meanDeviation(Indicator, int)
     */
    public static MeanDeviation md(Indicator<Num> indicator, int length) {
        return meanDeviation(indicator, length);
    }

    /**
     * Convenience static method for {@link #MeanDeviation(Indicator, int)}.
     */
    public static MeanDeviation meanDeviation(Indicator<Num> indicator, int length) {
        return new MeanDeviation(indicator, length);
    }

    private final Indicator<Num> indicator;
    private final int length;
    private final SimpleMovingAverage sma;

    /**
     * Instantiates a new {@link MeanDeviation}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public MeanDeviation(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
        sma = new SimpleMovingAverage(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        final Num mean = sma.getValue(index);
        final long startIndex = max(0, index - length + 1);
        final long observations = index - startIndex + 1;
        Num absoluteDeviations = numOfZero();
        for (long indicatorIndex = startIndex; indicatorIndex <= index; indicatorIndex++) {
            absoluteDeviations = absoluteDeviations
                    .add(indicator.getValue(indicatorIndex).subtract(mean).absoluteValue());
        }
        return absoluteDeviations.divide(observations);
    }
}
