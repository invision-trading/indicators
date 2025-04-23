package trade.invision.indicators.indicators.chaikin;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ad.AccumulationDistribution;
import trade.invision.indicators.indicators.ma.ema.ExponentialMovingAverage;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link ChaikinOscillator} is a {@link Num} {@link Indicator} to provide the Chaikin Oscillator (CO) over a
 * <code>shortEmaLength</code> and <code>longEmaLength</code> of {@link Bar}s.
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=ChaikinOscillator.htm">FM Labs</a>
 */
public class ChaikinOscillator extends Indicator<Num> {

    /**
     * @see #chaikinOscillator(BarSeries, int, int, Num)
     */
    public static ChaikinOscillator co(BarSeries barSeries, int shortEmaLength, int longEmaLength, Num smoothing) {
        return chaikinOscillator(barSeries, shortEmaLength, longEmaLength, smoothing);
    }

    /**
     * Convenience static method for {@link #ChaikinOscillator(BarSeries, int, int, Num)}.
     */
    public static ChaikinOscillator chaikinOscillator(BarSeries barSeries, int shortEmaLength, int longEmaLength,
            Num smoothing) {
        return new ChaikinOscillator(barSeries, shortEmaLength, longEmaLength, smoothing);
    }

    private final AccumulationDistribution ad;
    private final ExponentialMovingAverage emaShort;
    private final ExponentialMovingAverage emaLong;

    /**
     * Instantiates a new {@link ChaikinOscillator}.
     *
     * @param barSeries      the {@link BarSeries}
     * @param shortEmaLength the length of the short EMA (typically 3)
     * @param longEmaLength  the length of the long EMA (typically 10)
     * @param smoothing      the smoothing factor for the EMA (typically 2)
     */
    public ChaikinOscillator(BarSeries barSeries, int shortEmaLength, int longEmaLength, Num smoothing) {
        super(barSeries, max(shortEmaLength, longEmaLength));
        checkArgument(shortEmaLength > 0, "'shortEmaLength' must be greater than zero!");
        checkArgument(longEmaLength > 0, "'longEmaLength' must be greater than zero!");
        ad = new AccumulationDistribution(barSeries);
        emaShort = new ExponentialMovingAverage(ad, shortEmaLength, smoothing);
        emaLong = new ExponentialMovingAverage(ad, longEmaLength, smoothing);
    }

    @Override
    protected Num calculate(long index) {
        return emaShort.getValue(index).subtract(emaLong.getValue(index));
    }
}
