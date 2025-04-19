package trade.invision.indicators.indicators.bullishbearish.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link GlobalBearishPercentage} is a {@link Num} {@link Indicator} to provide the percentage of
 * {@link Bar#isBearish()} {@link Bar}s over all {@link Bar}s. The percentage is represented as a fractional. For
 * example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Reference</a>
 */
public class GlobalBearishPercentage extends AbstractGlobalBullishBearishPercentage {

    /**
     * Convenience static method for {@link #GlobalBearishPercentage(BarSeries)}.
     */
    public static GlobalBearishPercentage globalBearishPercentage(BarSeries barSeries) {
        return new GlobalBearishPercentage(barSeries);
    }

    /**
     * Instantiates a new {@link GlobalBearishPercentage}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public GlobalBearishPercentage(BarSeries barSeries) {
        super(barSeries, false);
    }
}
