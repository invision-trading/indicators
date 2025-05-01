package trade.invision.indicators.indicators.tsv;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.bar.Volume.volume;
import static trade.invision.indicators.indicators.closeprice.ClosePriceDifference.closePriceDifference;
import static trade.invision.indicators.indicators.cumulative.CumulativeSum.cumulativeSum;
import static trade.invision.indicators.indicators.operation.binary.NumBinaryOperations.multiply;

/**
 * {@link TimeSegmentedVolume} is a {@link Num} {@link Indicator} to provide the Time Segmented Volume (TSV) over a
 * <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.investopedia.com/terms/t/tsv.asp">Investopedia</a>
 */
public class TimeSegmentedVolume extends Indicator<Num> {

    /**
     * @see #timeSegmentedVolume(BarSeries, int)
     */
    public static TimeSegmentedVolume tsv(BarSeries barSeries, int length) {
        return timeSegmentedVolume(barSeries, length);
    }

    /**
     * Gets a {@link TimeSegmentedVolume}.
     *
     * @param barSeries the {@link BarSeries}
     * @param length    the number of values to look back at
     */
    public static TimeSegmentedVolume timeSegmentedVolume(BarSeries barSeries, int length) {
        return CACHE.get(new CacheKey(barSeries, length), key -> new TimeSegmentedVolume(barSeries, length));
    }

    private static final Cache<CacheKey, TimeSegmentedVolume> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int length;
    }

    private final Indicator<Num> tsv;

    protected TimeSegmentedVolume(BarSeries barSeries, int length) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        tsv = cumulativeSum(multiply(closePriceDifference(barSeries), volume(barSeries)), length);
    }

    @Override
    protected Num calculate(long index) {
        return tsv.getValue(index);
    }
}
