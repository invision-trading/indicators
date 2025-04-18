package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link StartIndexOf} is a {@link Num} {@link Indicator} to provide the {@link Series#getStartIndex()} of the given
 * {@link Series}.
 */
public class StartIndexOf extends CachelessIndicator<Num> {

    /**
     * Convenience static method for {@link #StartIndexOf(Series)}.
     */
    public static StartIndexOf startIndexOf(Series<?> series) {
        return new StartIndexOf(series);
    }

    /**
     * Instantiates a new {@link StartIndexOf}.
     *
     * @param series the {@link #getSeries()}
     */
    public StartIndexOf(Series<?> series) {
        super(series, 0);
        cache(true); // 'getStartIndex()' is mutable, so prefer to cache so same 'index' yields same result
    }

    @Override
    protected Num calculate(long index) {
        return numOf(series.getStartIndex());
    }
}
