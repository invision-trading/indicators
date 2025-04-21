package trade.invision.indicators.indicators.ma.ema;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link TripleExponentialMovingAverage} is a {@link Num} {@link Indicator} to provide a Triple Exponential Moving
 * Average (TEMA) over a <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/t/triple-exponential-moving-average.asp">Investopedia</a>
 */
public class TripleExponentialMovingAverage extends Indicator<Num> {

    /**
     * @see #tripleExponentialMovingAverage(Indicator, int)
     */
    public static TripleExponentialMovingAverage tema(Indicator<Num> indicator, int length) {
        return tripleExponentialMovingAverage(indicator, length);
    }

    /**
     * Convenience static method for {@link #TripleExponentialMovingAverage(Indicator, int)}.
     */
    public static TripleExponentialMovingAverage tripleExponentialMovingAverage(Indicator<Num> indicator, int length) {
        return new TripleExponentialMovingAverage(indicator, length);
    }

    /**
     * @see #tripleExponentialMovingAverage(Indicator, int, Num)
     */
    public static TripleExponentialMovingAverage tema(Indicator<Num> indicator, int length, Num smoothing) {
        return tripleExponentialMovingAverage(indicator, length, smoothing);
    }

    /**
     * Convenience static method for {@link #TripleExponentialMovingAverage(Indicator, int, Num)}.
     */
    public static TripleExponentialMovingAverage tripleExponentialMovingAverage(Indicator<Num> indicator, int length,
            Num smoothing) {
        return new TripleExponentialMovingAverage(indicator, length, smoothing);
    }

    private final ExponentialMovingAverage ema;
    private final ExponentialMovingAverage emaOfEma;
    private final ExponentialMovingAverage emaOfEmaOfEma;

    /**
     * Calls {@link #TripleExponentialMovingAverage(Indicator, int, Num)} with <code>smoothing</code> set to
     * <code>2</code>.
     */
    public TripleExponentialMovingAverage(Indicator<Num> indicator, int length) {
        this(indicator, length, indicator.getSeries().getNumFactory().two());
    }

    /**
     * Instantiates a new {@link TripleExponentialMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param smoothing the smoothing factor (typically 2)
     */
    public TripleExponentialMovingAverage(Indicator<Num> indicator, int length, Num smoothing) {
        super(indicator.getSeries(), length * 3);
        ema = new ExponentialMovingAverage(indicator, length, smoothing);
        emaOfEma = new ExponentialMovingAverage(ema, length, smoothing);
        emaOfEmaOfEma = new ExponentialMovingAverage(emaOfEma, length, smoothing);
    }

    @Override
    protected Num calculate(long index) {
        return numOfThree().multiply(ema.getValue(index).subtract(emaOfEma.getValue(index)))
                .add(emaOfEmaOfEma.getValue(index));
    }
}
