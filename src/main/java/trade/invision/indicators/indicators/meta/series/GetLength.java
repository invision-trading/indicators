package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.CachingIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link GetLength} is a {@link Num} {@link Indicator} to provide the {@link Series#getLength()} of the given
 * {@link Series}.
 */
public class GetLength extends CachingIndicator<Num> { // Cache so same 'index' yields same result

    /**
     * Convenience static method for {@link #GetLength(Series)}.
     */
    public static GetLength getLength(Series<?> series) {
        return new GetLength(series);
    }

    /**
     * Instantiates a new {@link GetLength}.
     *
     * @param series the {@link #getSeries()}
     */
    public GetLength(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Num calculate(long index) {
        return numOf(series.getLength());
    }
}
