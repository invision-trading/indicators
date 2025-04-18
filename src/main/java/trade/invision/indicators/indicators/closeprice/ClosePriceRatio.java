package trade.invision.indicators.indicators.closeprice;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Close;
import trade.invision.indicators.indicators.previous.PreviousRatio;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link ClosePriceRatio} is a {@link Num} {@link Indicator} to use {@link PreviousRatio} with {@link Close}.
 */
public class ClosePriceRatio extends PreviousRatio {

    /**
     * Convenience static method for {@link #ClosePriceRatio(BarSeries)}.
     */
    public static ClosePriceRatio closePriceRatio(BarSeries barSeries) {
        return new ClosePriceRatio(barSeries);
    }

    /**
     * Convenience static method for {@link #ClosePriceRatio(BarSeries, int)}.
     */
    public static ClosePriceRatio closePriceRatio(BarSeries barSeries, int length) {
        return new ClosePriceRatio(barSeries, length);
    }

    /**
     * Instantiates a new {@link ClosePriceRatio} with <code>n</code> set to <code>1</code>.
     *
     * @param barSeries the {@link BarSeries}
     */
    public ClosePriceRatio(BarSeries barSeries) {
        this(barSeries, 1);
    }

    /**
     * Instantiates a new {@link ClosePriceRatio}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public ClosePriceRatio(BarSeries barSeries, int n) {
        super(new Close(barSeries), n);
    }
}
