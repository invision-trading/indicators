package trade.invision.indicators.indicators.random;

import trade.invision.indicators.indicators.CachingIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;

import java.util.concurrent.ThreadLocalRandom;

/**
 * {@link RandomBoolean} is a {@link Boolean} {@link Indicator} to provide a random {@link Boolean} for each
 * <code>index</code>.
 */
public class RandomBoolean extends CachingIndicator<Boolean> { // Cache so same 'index' yields same result

    /**
     * Convenience static method for {@link #RandomBoolean(Series)}.
     */
    public static RandomBoolean randomBoolean(Series<?> series) {
        return new RandomBoolean(series);
    }

    /**
     * Instantiates a new {@link RandomBoolean}.
     *
     * @param series the {@link #getSeries()}
     */
    public RandomBoolean(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Boolean calculate(long index) {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
