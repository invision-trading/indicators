package trade.invision.indicators.indicators.draw.percentage.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link GlobalDrawupPercentage} is a {@link Num} {@link Indicator} to provide the global drawup percentage. This is
 * also known as the maximum drawup (MDU). The percentage is represented as a fractional. For example, a provided value
 * of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/m/maximum-drawdown-mdd.asp">Reference</a>
 */
public class GlobalDrawupPercentage extends AbstractDrawupDrawdown {

    /**
     * @see #globalDrawupPercentage(Indicator)
     */
    public static GlobalDrawupPercentage mdu(Indicator<Num> indicator) {
        return globalDrawupPercentage(indicator);
    }

    /**
     * Convenience static method for {@link #GlobalDrawupPercentage(Indicator)}.
     */
    public static GlobalDrawupPercentage globalDrawupPercentage(Indicator<Num> indicator) {
        return new GlobalDrawupPercentage(indicator);
    }

    /**
     * Instantiates a new {@link GlobalDrawupPercentage}.
     *
     * @param indicator the {@link Indicator}
     */
    public GlobalDrawupPercentage(Indicator<Num> indicator) {
        super(indicator, null, true, true);
    }
}
