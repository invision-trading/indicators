package trade.invision.indicators.indicators.meta.indicator;

import trade.invision.indicators.indicators.Indicator;

/**
 * {@link IsCaching} is a {@link Boolean} {@link Indicator} to check if the given {@link Indicator}
 * {@link #isCaching()}.
 */
public class IsCaching extends Indicator<Boolean> {

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
        cache(true); // 'isCaching()' is mutable, so prefer to cache result for each 'index'
    }

    @Override
    protected Boolean calculate(long index) {
        return indicator.isCaching();
    }
}
