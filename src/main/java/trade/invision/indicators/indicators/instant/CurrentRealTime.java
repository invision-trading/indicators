package trade.invision.indicators.indicators.instant;

import trade.invision.indicators.indicators.CachingIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;

import java.time.Instant;

import static java.time.Instant.now;

/**
 * {@link CurrentRealTime} is an {@link Instant} {@link Indicator} to provide the current real time.
 */
public class CurrentRealTime extends CachingIndicator<Instant> { // Cache so same 'index' yields same result

    /**
     * Convenience static method for {@link #CurrentRealTime(Series)}.
     */
    public static CurrentRealTime currentRealTime(Series<?> series) {
        return new CurrentRealTime(series);
    }

    /**
     * Instantiates a new {@link CurrentRealTime}.
     *
     * @param series the {@link #getSeries()}
     */
    public CurrentRealTime(Series<?> series) {
        super(series, 0);
    }

    @Override
    protected Instant calculate(long index) {
        return now();
    }
}
