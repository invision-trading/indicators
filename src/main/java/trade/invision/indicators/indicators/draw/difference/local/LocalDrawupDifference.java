package trade.invision.indicators.indicators.draw.difference.local;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link LocalDrawupDifference} is a {@link Num} {@link Indicator} to provide the local drawup difference. This is
 * similar to the local drawup percentage (MDD), but instead of providing a percentage, this provides a difference
 * (subtraction).
 *
 * @see <a href="https://www.investopedia.com/terms/d/drawdown.asp">Investopedia</a>
 */
public class LocalDrawupDifference extends AbstractDrawupDrawdown {

    /**
     * Convenience static method for {@link #LocalDrawupDifference(Indicator, int)}.
     */
    public static LocalDrawupDifference localDrawupDifference(Indicator<Num> indicator, int length) {
        return new LocalDrawupDifference(indicator, length);
    }

    /**
     * Instantiates a new {@link LocalDrawupDifference}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public LocalDrawupDifference(Indicator<Num> indicator, int length) {
        super(indicator, length, true, false);
    }
}
