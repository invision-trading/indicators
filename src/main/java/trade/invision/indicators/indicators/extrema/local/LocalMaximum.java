package trade.invision.indicators.indicators.extrema.local;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link LocalMaximum} is a {@link Num} {@link Indicator} to provide the local maximum extrema (highest value) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Maximum_and_minimum">Reference</a>
 */
public class LocalMaximum extends AbstractLocalExtrema {

    /**
     * Convenience static method for {@link #LocalMaximum(Indicator, int)}.
     */
    public static LocalMaximum localMaximum(Indicator<Num> indicator, int length) {
        return new LocalMaximum(indicator, length);
    }

    /**
     * Instantiates a new {@link LocalMaximum}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public LocalMaximum(Indicator<Num> indicator, int length) {
        super(indicator, length, true);
    }
}
