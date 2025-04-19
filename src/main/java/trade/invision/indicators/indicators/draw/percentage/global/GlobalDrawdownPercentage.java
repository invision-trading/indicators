package trade.invision.indicators.indicators.draw.percentage.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link GlobalDrawdownPercentage} is a {@link Num} {@link Indicator} to provide the global drawdown percentage. This
 * is also known as the maximum drawdown (MDD). The percentage is represented as a fractional. For example, a provided
 * value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/m/maximum-drawdown-mdd.asp">Reference</a>
 */
public class GlobalDrawdownPercentage extends AbstractDrawupDrawdown {

    /**
     * @see #globalDrawdownPercentage(Indicator)
     */
    public static GlobalDrawdownPercentage mdd(Indicator<Num> indicator) {
        return globalDrawdownPercentage(indicator);
    }

    /**
     * Convenience static method for {@link #GlobalDrawdownPercentage(Indicator)}.
     */
    public static GlobalDrawdownPercentage globalDrawdownPercentage(Indicator<Num> indicator) {
        return new GlobalDrawdownPercentage(indicator);
    }

    /**
     * Instantiates a new {@link GlobalDrawdownPercentage}.
     *
     * @param indicator the {@link Indicator}
     */
    public GlobalDrawdownPercentage(Indicator<Num> indicator) {
        super(indicator, null, false, true);
    }
}
