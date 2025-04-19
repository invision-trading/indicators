package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link GetEndIndex} is a {@link Num} {@link Indicator} to provide the {@link Series#getEndIndex()} of the given
 * {@link Series}.
 */
public class GetEndIndex extends CachelessIndicator<Num> {

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
        cache(true); // 'getEndIndex()' is mutable, so prefer to cache so same 'index' yields same result
    }

    @Override
    protected Num calculate(long index) {
        return numOf(series.getEndIndex());
    }
}
