package trade.invision.indicators.indicators.meta.indicator;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link CurrentIndex} is a {@link Num} {@link Indicator} to provide the current <code>index</code> of the
 * {@link #getValue(long)} call.
 */
public class CurrentIndex extends CachelessIndicator<Num> {

    /**
     * Convenience static method for {@link #CurrentIndex(Series)}.
     */
    public static CurrentIndex currentIndex(Series<?> series) {
        return new CurrentIndex(series);
    }

    /**
     * Instantiates a new {@link CurrentIndex}.
     *
     * @param series the {@link #getSeries()}
     */
    public CurrentIndex(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Num calculate(long index) {
        return numOf(index);
    }
}
