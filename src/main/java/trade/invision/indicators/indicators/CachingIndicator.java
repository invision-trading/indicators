package trade.invision.indicators.indicators;

import trade.invision.indicators.series.Series;

/**
 * {@link CachingIndicator} is an abstract {@link Indicator} class that permanently enables caching. This is useful for
 * {@link Indicator} implementations that require caching of previously calculated values, such as for recursion.
 *
 * @param <T> the {@link Indicator} type
 */
public abstract class CachingIndicator<T> extends Indicator<T> {

    /**
     * @see Indicator#Indicator(Series, int)
     */
    public CachingIndicator(Series<?> series, int minimumStableIndex) {
        super(series, minimumStableIndex);
        caching();
    }
}
