package trade.invision.indicators.indicators.random;

import trade.invision.indicators.indicators.CachingIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;
import trade.invision.num.NumFactory;

/**
 * {@link RandomNum} is a {@link Num} {@link Indicator} to provide {@link NumFactory#random()} for each
 * <code>index</code>.
 */
public class RandomNum extends CachingIndicator<Num> { // Cache so same 'index' yields same result

    /**
     * Convenience static method for {@link #RandomNum(Series)}.
     */
    public static RandomNum randomNum(Series<?> series) {
        return new RandomNum(series);
    }

    /**
     * Instantiates a new {@link RandomNum}.
     *
     * @param series the {@link #getSeries()}
     */
    public RandomNum(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Num calculate(long index) {
        return series.getNumFactory().random();
    }
}
