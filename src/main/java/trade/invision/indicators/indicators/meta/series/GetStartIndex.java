package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link GetStartIndex} is a {@link Num} {@link Indicator} to provide the {@link Series#getStartIndex()} of the given
 * {@link Series}.
 */
public class GetStartIndex extends CachelessIndicator<Num> {

    /**
     * Convenience static method for {@link #GetStartIndex(Series)}.
     */
    public static GetStartIndex getStartIndex(Series<?> series) {
        return new GetStartIndex(series);
    }

    /**
     * Instantiates a new {@link GetStartIndex}.
     *
     * @param series the {@link #getSeries()}
     */
    public GetStartIndex(Series<?> series) {
        super(series, 0);
        cache(true); // 'getStartIndex()' is mutable, so prefer to cache so same 'index' yields same result
    }

    @Override
    protected Num calculate(long index) {
        return numOf(series.getStartIndex());
    }
}
