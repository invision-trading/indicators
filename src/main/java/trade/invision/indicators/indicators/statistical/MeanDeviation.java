package trade.invision.indicators.indicators.statistical;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.sma.SimpleMovingAverage;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static trade.invision.indicators.indicators.ma.sma.SimpleMovingAverage.simpleMovingAverage;

/**
 * {@link MeanDeviation} is a {@link Num} {@link Indicator} to provide the statistical mean deviation (MD) over a
 * <code>length</code> of values. This is also known as the average absolute deviation.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Average_absolute_deviation">Wikipedia</a>
 */
public class MeanDeviation extends Indicator<Num> {

    /**
     * @see #meanDeviation(Indicator, int)
     */
    public static MeanDeviation md(Indicator<Num> indicator, int length) {
        return meanDeviation(indicator, length);
    }

    /**
     * Gets a {@link MeanDeviation}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static MeanDeviation meanDeviation(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new MeanDeviation(indicator, length));
    }

    private static final Cache<CacheKey, MeanDeviation> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final Indicator<Num> indicator;
    private final int length;
    private final SimpleMovingAverage sma;

    protected MeanDeviation(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
        sma = simpleMovingAverage(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        final Num mean = sma.getValue(index);
        final long startIndex = max(0, index - length + 1);
        final long observations = index - startIndex + 1;
        Num absoluteDeviations = numOfZero();
        for (long indicatorIndex = startIndex; indicatorIndex <= index; indicatorIndex++) {
            absoluteDeviations = absoluteDeviations
                    .add(indicator.getValue(indicatorIndex).subtract(mean).absoluteValue());
        }
        return absoluteDeviations.divide(observations);
    }
}
