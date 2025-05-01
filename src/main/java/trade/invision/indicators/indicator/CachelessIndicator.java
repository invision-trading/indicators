package trade.invision.indicators.indicator;

import trade.invision.indicators.series.Series;

/**
 * {@link CachelessIndicator} is an abstract {@link Indicator} class that permanently disables caching. This is useful
 * for {@link Indicator} implementations that derive their calculated values from existing data without any
 * modifications.
 *
 * @param <T> the {@link Indicator} type
 */
public abstract class CachelessIndicator<T> extends Indicator<T> {

    /**
     * @see Indicator#Indicator(Series, int)
     */
    public CachelessIndicator(Series<?> series, int minimumStableIndex) {
        super(series, minimumStableIndex);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A extends Indicator<T>> A caching() {
        return (A) this;
    }
}
