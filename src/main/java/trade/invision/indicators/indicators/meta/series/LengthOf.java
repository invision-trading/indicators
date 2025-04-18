package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link LengthOf} is a {@link Num} {@link Indicator} to provide the {@link Series#getLength()} of the given
 * {@link Series}.
 */
public class LengthOf extends CachelessIndicator<Num> {

    /**
     * Convenience static method for {@link #LengthOf(Series)}.
     */
    public static LengthOf lengthOf(Series<?> series) {
        return new LengthOf(series);
    }

    /**
     * Instantiates a new {@link LengthOf}.
     *
     * @param series the {@link #getSeries()}
     */
    public LengthOf(Series<?> series) {
        super(series, 0);
        cache(true); // 'getLength()' is mutable, so prefer to cache so same 'index' yields same result
    }

    @Override
    protected Num calculate(long index) {
        return numOf(series.getLength());
    }
}
