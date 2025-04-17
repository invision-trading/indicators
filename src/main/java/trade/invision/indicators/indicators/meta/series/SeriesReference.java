package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;

/**
 * {@link SeriesReference} is an {@link Indicator} to provide values from the given {@link Series}.
 *
 * @param <T> the type
 */
public class SeriesReference<T> extends CachelessIndicator<T> {

    /**
     * Convenience static method for {@link #SeriesReference(Series)}.
     */
    public static <T> SeriesReference<T> seriesReference(Series<T> series) {
        return new SeriesReference<>(series);
    }

    /**
     * Instantiates a new {@link SeriesReference}.
     *
     * @param series the {@link #getSeries()}
     */
    public SeriesReference(Series<T> series) {
        super(series, 0);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected T calculate(long index) {
        return (T) series.get(index);
    }
}
