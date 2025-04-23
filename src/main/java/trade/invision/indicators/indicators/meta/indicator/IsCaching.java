package trade.invision.indicators.indicators.meta.indicator;

import trade.invision.indicators.indicators.CachingIndicator;
import trade.invision.indicators.indicators.Indicator;

/**
 * {@link IsCaching} is a {@link Boolean} {@link Indicator} to check if the given {@link Indicator}
 * {@link #isCaching()}.
 */
public class IsCaching extends CachingIndicator<Boolean> { // Cache so same 'index' yields same result

    /**
     * Convenience static method for {@link #IsCaching(Indicator)}.
     */
    public static IsCaching isCaching(Indicator<?> indicator) {
        return new IsCaching(indicator);
    }

    private final Indicator<?> indicator;

    /**
     * Instantiates a new {@link IsCaching}.
     *
     * @param indicator the {@link Indicator}
     */
    public IsCaching(Indicator<?> indicator) {
        super(indicator.getSeries(), 0);
        this.indicator = indicator;
    }

    @Override
    protected Boolean calculate(long index) {
        return indicator.isCaching();
    }
}
