package trade.invision.indicators.indicators.extrema.local;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link LocalMinimum} is a {@link Num} {@link Indicator} to provide the local minimum extrema (lowest value) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Maximum_and_minimum">Wikipedia</a>
 */
public class LocalMinimum extends AbstractLocalExtrema {

    /**
     * Convenience static method for {@link #LocalMinimum(Indicator, int)}.
     */
    public static LocalMinimum localMinimum(Indicator<Num> indicator, int length) {
        return new LocalMinimum(indicator, length);
    }

    /**
     * Instantiates a new {@link LocalMinimum}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public LocalMinimum(Indicator<Num> indicator, int length) {
        super(indicator, length, false);
    }
}
