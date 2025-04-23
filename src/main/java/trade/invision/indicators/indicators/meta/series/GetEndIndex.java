package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.CachingIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link GetEndIndex} is a {@link Num} {@link Indicator} to provide the {@link Series#getEndIndex()} of the given
 * {@link Series}.
 */
public class GetEndIndex extends CachingIndicator<Num> { // Cache so same 'index' yields same result

    /**
     * Convenience static method for {@link #GetEndIndex(Series)}.
     */
    public static GetEndIndex getEndIndex(Series<?> series) {
        return new GetEndIndex(series);
    }

    /**
     * Instantiates a new {@link GetEndIndex}.
     *
     * @param series the {@link #getSeries()}
     */
    public GetEndIndex(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Num calculate(long index) {
        return numOf(series.getEndIndex());
    }
}
