package trade.invision.indicators.indicator.meta.indicator;

import trade.invision.indicators.indicator.CachelessIndicator;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link CurrentIndex} is a {@link Num} {@link Indicator} to provide the current <code>index</code> of the
 * {@link #getValue(long)} call.
 */
public class CurrentIndex extends CachelessIndicator<Num> {

    /**
     * Gets a {@link CurrentIndex}.
     *
     * @param series the {@link #getSeries()}
     */
    public static CurrentIndex currentIndex(Series<?> series) {
        return new CurrentIndex(series);
    }

    protected CurrentIndex(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Num calculate(long index) {
        return numOf(index);
    }
}
