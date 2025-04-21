package trade.invision.indicators.indicators.closeprice;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Close;
import trade.invision.indicators.indicators.previous.PreviousPrecentChange;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link ClosePricePercentChange} is a {@link Num} {@link Indicator} to use {@link PreviousPrecentChange} with
 * {@link Close}. This is also referred to as Rate of Change (ROC) or Momentum. The percentage is represented as a
 * fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 */
public class ClosePricePercentChange extends PreviousPrecentChange {

    /**
     * Convenience static method for {@link #ClosePricePercentChange(BarSeries)}.
     */
    public static ClosePricePercentChange closePricePercentChange(BarSeries barSeries) {
        return new ClosePricePercentChange(barSeries);
    }

    /**
     * Convenience static method for {@link #ClosePricePercentChange(BarSeries, int)}.
     */
    public static ClosePricePercentChange closePricePercentChange(BarSeries barSeries, int n) {
        return new ClosePricePercentChange(barSeries, n);
    }

    /**
     * Instantiates a new {@link ClosePricePercentChange} with <code>n</code> set to <code>1</code>.
     *
     * @param barSeries the {@link BarSeries}
     */
    public ClosePricePercentChange(BarSeries barSeries) {
        this(barSeries, 1);
    }

    /**
     * Instantiates a new {@link ClosePricePercentChange}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public ClosePricePercentChange(BarSeries barSeries, int n) {
        super(new Close(barSeries), n);
    }
}
