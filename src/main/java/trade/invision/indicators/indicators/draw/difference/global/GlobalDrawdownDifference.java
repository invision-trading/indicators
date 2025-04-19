package trade.invision.indicators.indicators.draw.difference.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link GlobalDrawdownDifference} is a {@link Num} {@link Indicator} to provide the global drawdown difference. This
 * is similar to the global drawdown percentage, but instead of providing a percentage, this provides a difference
 * (subtraction).
 *
 * @see <a href="https://www.investopedia.com/terms/m/maximum-drawdown-mdd.asp">Reference</a>
 */
public class GlobalDrawdownDifference extends AbstractDrawupDrawdown {

    /**
     * Convenience static method for {@link #GlobalDrawdownDifference(Indicator)}.
     */
    public static GlobalDrawdownDifference globalDrawdownDifference(Indicator<Num> indicator) {
        return new GlobalDrawdownDifference(indicator);
    }

    /**
     * Instantiates a new {@link GlobalDrawdownDifference}.
     *
     * @param indicator the {@link Indicator}
     */
    public GlobalDrawdownDifference(Indicator<Num> indicator) {
        super(indicator, null, false, false);
    }
}
