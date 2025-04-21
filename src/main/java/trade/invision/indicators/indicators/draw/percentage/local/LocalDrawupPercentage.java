package trade.invision.indicators.indicators.draw.percentage.local;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link LocalDrawupPercentage} is a {@link Num} {@link Indicator} to provide the local drawup percentage. This is also
 * known as the drawup (DU). The percentage is represented as a fractional. For example, a provided value of
 * <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/d/drawdown.asp">Investopedia</a>
 */
public class LocalDrawupPercentage extends AbstractDrawupDrawdown {

    /**
     * @see #localDrawupPercentage(Indicator, int)
     */
    public static LocalDrawupPercentage du(Indicator<Num> indicator, int length) {
        return localDrawupPercentage(indicator, length);
    }

    /**
     * Convenience static method for {@link #LocalDrawupPercentage(Indicator, int)}.
     */
    public static LocalDrawupPercentage localDrawupPercentage(Indicator<Num> indicator, int length) {
        return new LocalDrawupPercentage(indicator, length);
    }

    /**
     * Instantiates a new {@link LocalDrawupPercentage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public LocalDrawupPercentage(Indicator<Num> indicator, int length) {
        super(indicator, length, true, true);
    }
}
