package trade.invision.indicators.indicators.extrema.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link GlobalMaximum} is a {@link Num} {@link Indicator} to provide the global maximum extrema (all-time highest
 * value).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Maximum_and_minimum">Wikipedia</a>
 */
public class GlobalMaximum extends AbstractGlobalExtrema {

    /**
     * Convenience static method for {@link #GlobalMaximum(Indicator)}.
     */
    public static GlobalMaximum globalMaximum(Indicator<Num> indicator) {
        return new GlobalMaximum(indicator);
    }

    /**
     * Instantiates a new {@link GlobalMaximum}.
     *
     * @param indicator the {@link Indicator}
     */
    public GlobalMaximum(Indicator<Num> indicator) {
        super(indicator, true);
    }
}
