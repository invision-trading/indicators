package trade.invision.indicators.indicators.draw.percentage.local;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.draw.AbstractDrawupDrawdown;
import trade.invision.num.Num;

/**
 * {@link LocalDrawdownPercentage} is a {@link Num} {@link Indicator} to provide the local drawdown percentage. This is
 * also known as the drawdown (DD). The percentage is represented as a fractional. For example, a provided value of
 * <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/d/drawdown.asp">Reference</a>
 */
public class LocalDrawdownPercentage extends AbstractDrawupDrawdown {

    /**
     * @see #localDrawdownPercentage(Indicator, int)
     */
    public static LocalDrawdownPercentage dd(Indicator<Num> indicator, int length) {
        return localDrawdownPercentage(indicator, length);
    }

    /**
     * Convenience static method for {@link #LocalDrawdownPercentage(Indicator, int)}.
     */
    public static LocalDrawdownPercentage localDrawdownPercentage(Indicator<Num> indicator, int length) {
        return new LocalDrawdownPercentage(indicator, length);
    }

    /**
     * Instantiates a new {@link LocalDrawdownPercentage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public LocalDrawdownPercentage(Indicator<Num> indicator, int length) {
        super(indicator, length, false, true);
    }
}
