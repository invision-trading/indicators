package trade.invision.indicators.indicators.ma.sma;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.cumulative.CumulativeSum;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.min;

/**
 * {@link SimpleMovingAverage} is a {@link Num} {@link Indicator} to provide a Simple Moving Average (SMA) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/s/sma.asp">Investopedia</a>
 */
public class SimpleMovingAverage extends Indicator<Num> {

    /**
     * @see #simpleMovingAverage(Indicator, int)
     */
    public static SimpleMovingAverage sma(Indicator<Num> indicator, int length) {
        return simpleMovingAverage(indicator, length);
    }

    /**
     * Convenience static method for {@link #SimpleMovingAverage(Indicator, int)}.
     */
    public static SimpleMovingAverage simpleMovingAverage(Indicator<Num> indicator, int length) {
        return new SimpleMovingAverage(indicator, length);
    }

    private final int length;
    private final CumulativeSum sum;

    /**
     * Instantiates a new {@link SimpleMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public SimpleMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.length = length;
        sum = new CumulativeSum(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        return sum.getValue(index).divide(min(index + 1, length));
    }
}
