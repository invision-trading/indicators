package trade.invision.indicators.indicators.wpr;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.extrema.local.LocalMaximum;
import trade.invision.indicators.indicators.extrema.local.LocalMinimum;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link WilliamsPercentRange} is a {@link Num} {@link Indicator} to provide the Williams Percent Range (WPR) over a
 * <code>length</code> of values. Also known as the Williams %R. The percentage is represented as a
 * fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/w/williamsr.asp">Investopedia</a>
 */
public class WilliamsPercentRange extends Indicator<Num> {

    /**
     * @see #williamsPercentRange(Indicator, int)
     */
    public static WilliamsPercentRange wpr(Indicator<Num> indicator, int length) {
        return williamsPercentRange(indicator, length);
    }

    /**
     * Convenience static method for {@link #WilliamsPercentRange(Indicator, int)}.
     */
    public static WilliamsPercentRange williamsPercentRange(Indicator<Num> indicator, int length) {
        return new WilliamsPercentRange(indicator, length);
    }

    private final Indicator<Num> indicator;
    private final LocalMaximum highestHigh;
    private final LocalMinimum lowestLow;
    private final Num negativeHundred;

    /**
     * Instantiates a new {@link WilliamsPercentRange}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at (typically 14)
     */
    public WilliamsPercentRange(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        highestHigh = new LocalMaximum(indicator, length);
        lowestLow = new LocalMinimum(indicator, length);
        negativeHundred = numOf(-100);
    }

    @Override
    protected Num calculate(long index) {
        final Num value = indicator.getValue(index);
        final Num highestHighValue = highestHigh.getValue(index);
        final Num lowestLowValue = lowestLow.getValue(index);
        return highestHighValue.subtract(value).divide(highestHighValue.subtract(lowestLowValue))
                .multiply(negativeHundred);
    }
}
