package trade.invision.indicators.indicators.closeprice;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Close;
import trade.invision.indicators.indicators.previous.PreviousDifference;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link ClosePriceDifference} is a {@link Num} {@link Indicator} to use {@link PreviousDifference} with
 * {@link Close}.
 */
public class ClosePriceDifference extends PreviousDifference {

    /**
     * Convenience static method for {@link #ClosePriceDifference(BarSeries)}.
     */
    public static ClosePriceDifference closePriceDifference(BarSeries barSeries) {
        return new ClosePriceDifference(barSeries);
    }

    /**
     * Convenience static method for {@link #ClosePriceDifference(BarSeries, int)}.
     */
    public static ClosePriceDifference closePriceDifference(BarSeries barSeries, int length) {
        return new ClosePriceDifference(barSeries, length);
    }

    /**
     * Instantiates a new {@link ClosePriceDifference} with <code>n</code> set to <code>1</code>.
     *
     * @param barSeries the {@link BarSeries}
     */
    public ClosePriceDifference(BarSeries barSeries) {
        this(barSeries, 1);
    }

    /**
     * Instantiates a new {@link ClosePriceDifference}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public ClosePriceDifference(BarSeries barSeries, int n) {
        super(new Close(barSeries), n);
    }
}
