package trade.invision.indicators.indicators.random;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;

import java.util.concurrent.ThreadLocalRandom;

/**
 * {@link RandomBoolean} is a {@link Boolean} {@link Indicator} to provide a random {@link Boolean} for each
 * <code>index</code>.
 */
public class RandomBoolean extends Indicator<Boolean> {

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
        cache(true); // Prefer to cache so same 'index' yields same result
    }

    @Override
    protected Boolean calculate(long index) {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
