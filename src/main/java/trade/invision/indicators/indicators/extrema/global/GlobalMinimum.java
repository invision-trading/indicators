package trade.invision.indicators.indicators.extrema.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link GlobalMinimum} is a {@link Num} {@link Indicator} to provide the global minimum extrema (all-time lowest
 * value).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Maximum_and_minimum">Wikipedia</a>
 */
public class GlobalMinimum extends AbstractGlobalExtrema {

    /**
     * Convenience static method for {@link #GlobalMinimum(Indicator)}.
     */
    public static GlobalMinimum globalMinimum(Indicator<Num> indicator) {
        return new GlobalMinimum(indicator);
    }

    /**
     * Instantiates a new {@link GlobalMinimum}.
     *
     * @param indicator the {@link Indicator}
     */
    public GlobalMinimum(Indicator<Num> indicator) {
        super(indicator, false);
    }
}
