package trade.invision.indicators.indicators.draw.difference.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link GlobalDrawupDifference} is a {@link Num} {@link Indicator} to provide the global drawup difference. This is
 * similar to the global drawup percentage (MDU), but instead of providing a percentage, this provides a difference
 * (subtraction).
 *
 * @see <a href="https://www.investopedia.com/terms/m/maximum-drawdown-mdd.asp">Reference</a>
 */
public class GlobalDrawupDifference extends AbstractDrawupDrawdown {

    /**
     * Convenience static method for {@link #GlobalDrawupDifference(Indicator)}.
     */
    public static GlobalDrawupDifference globalDrawupDifference(Indicator<Num> indicator) {
        return new GlobalDrawupDifference(indicator);
    }

    /**
     * Instantiates a new {@link GlobalDrawupDifference}.
     *
     * @param indicator the {@link Indicator}
     */
    public GlobalDrawupDifference(Indicator<Num> indicator) {
        super(indicator, null, true, false);
    }
}
