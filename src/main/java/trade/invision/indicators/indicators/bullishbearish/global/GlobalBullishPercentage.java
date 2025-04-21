package trade.invision.indicators.indicators.bullishbearish.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link GlobalBullishPercentage} is a {@link Num} {@link Indicator} to provide the percentage of
 * {@link Bar#isBullish()} {@link Bar}s over all {@link Bar}s. The percentage is represented as a fractional. For
 * example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class GlobalBullishPercentage extends AbstractGlobalBullishBearishPercentage {

    /**
     * Convenience static method for {@link #GlobalBullishPercentage(BarSeries)}.
     */
    public static GlobalBullishPercentage globalBullishPercentage(BarSeries barSeries) {
        return new GlobalBullishPercentage(barSeries);
    }

    /**
     * Instantiates a new {@link GlobalBullishPercentage}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public GlobalBullishPercentage(BarSeries barSeries) {
        super(barSeries, true);
    }
}
