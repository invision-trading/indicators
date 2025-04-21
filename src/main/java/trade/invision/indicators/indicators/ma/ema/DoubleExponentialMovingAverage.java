package trade.invision.indicators.indicators.ma.ema;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link DoubleExponentialMovingAverage} is a {@link Num} {@link Indicator} to provide a Double Exponential Moving
 * Average (DEMA) over a <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/d/double-exponential-moving-average.asp">Investopedia</a>
 */
public class DoubleExponentialMovingAverage extends Indicator<Num> {

    /**
     * @see #doubleExponentialMovingAverage(Indicator, int)
     */
    public static DoubleExponentialMovingAverage dema(Indicator<Num> indicator, int length) {
        return doubleExponentialMovingAverage(indicator, length);
    }

    /**
     * Convenience static method for {@link #DoubleExponentialMovingAverage(Indicator, int)}.
     */
    public static DoubleExponentialMovingAverage doubleExponentialMovingAverage(Indicator<Num> indicator, int length) {
        return new DoubleExponentialMovingAverage(indicator, length);
    }

    /**
     * @see #doubleExponentialMovingAverage(Indicator, int, Num)
     */
    public static DoubleExponentialMovingAverage dema(Indicator<Num> indicator, int length, Num smoothing) {
        return doubleExponentialMovingAverage(indicator, length, smoothing);
    }

    /**
     * Convenience static method for {@link #DoubleExponentialMovingAverage(Indicator, int, Num)}.
     */
    public static DoubleExponentialMovingAverage doubleExponentialMovingAverage(Indicator<Num> indicator, int length,
            Num smoothing) {
        return new DoubleExponentialMovingAverage(indicator, length, smoothing);
    }

    private final ExponentialMovingAverage ema;
    private final ExponentialMovingAverage emaOfEma;

    /**
     * Calls {@link #DoubleExponentialMovingAverage(Indicator, int, Num)} with <code>smoothing</code> set to
     * <code>2</code>.
     */
    public DoubleExponentialMovingAverage(Indicator<Num> indicator, int length) {
        this(indicator, length, indicator.getSeries().getNumFactory().two());
    }

    /**
     * Instantiates a new {@link DoubleExponentialMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param smoothing the smoothing factor (typically 2)
     */
    public DoubleExponentialMovingAverage(Indicator<Num> indicator, int length, Num smoothing) {
        super(indicator.getSeries(), length * 2);
        checkArgument(length > 0, "'length' must be greater than zero!");
        ema = new ExponentialMovingAverage(indicator, length, smoothing);
        emaOfEma = new ExponentialMovingAverage(ema, length, smoothing);
    }

    @Override
    protected Num calculate(long index) {
        return numOfTwo().multiply(ema.getValue(index)).subtract(emaOfEma.getValue(index));
    }
}
