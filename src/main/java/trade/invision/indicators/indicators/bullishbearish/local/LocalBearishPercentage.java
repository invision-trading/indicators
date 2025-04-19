package trade.invision.indicators.indicators.bullishbearish.local;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link LocalBearishPercentage} is a {@link Num} {@link Indicator} to provide the percentage of
 * {@link Bar#isBearish()} {@link Bar}s over a <code>length</code> of {@link Bar}s. The percentage is represented as a
 * fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Reference</a>
 */
public class LocalBearishPercentage extends AbstractLocalBullishBearishPercentage {

    /**
     * Convenience static method for {@link #LocalBearishPercentage(BarSeries, int)}.
     */
    public static LocalBearishPercentage localBearishPercentage(BarSeries barSeries, int length) {
        return new LocalBearishPercentage(barSeries, length);
    }

    /**
     * Instantiates a new {@link LocalBearishPercentage}.
     *
     * @param barSeries the {@link BarSeries}
     * @param length    the number of values to look back at
     */
    public LocalBearishPercentage(BarSeries barSeries, int length) {
        super(barSeries, length, false);
    }
}
