package trade.invision.indicators.indicators.chaikin;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.cumulative.CumulativeSum;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.bar.Volume.volume;
import static trade.invision.indicators.indicators.clv.CloseLocationValue.closeLocationValue;
import static trade.invision.indicators.indicators.cumulative.CumulativeSum.cumulativeSum;
import static trade.invision.indicators.indicators.operation.binary.NumBinaryOperations.multiply;

/**
 * {@link ChaikinMoneyFlow} is a {@link Num} {@link Indicator} to provide the Chaikin Money Flow (CMF) over a
 * <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=ChaikinMoneyFlow.htm">FM Labs</a>
 */
public class ChaikinMoneyFlow extends Indicator<Num> {

    /**
     * @see #chaikinMoneyFlow(BarSeries, int)
     */
    public static ChaikinMoneyFlow cmf(BarSeries barSeries, int length) {
        return chaikinMoneyFlow(barSeries, length);
    }

    /**
     * Gets a {@link ChaikinMoneyFlow}.
     *
     * @param barSeries the {@link BarSeries}
     * @param length    the number of values to look back at
     */
    public static ChaikinMoneyFlow chaikinMoneyFlow(BarSeries barSeries, int length) {
        return CACHE.get(new CacheKey(barSeries, length), key -> new ChaikinMoneyFlow(barSeries, length));
    }

    private static final Cache<CacheKey, ChaikinMoneyFlow> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int length;
    }

    private final CumulativeSum cumulativeNumerator;
    private final CumulativeSum cumulativeVolume;

    protected ChaikinMoneyFlow(BarSeries barSeries, int length) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        final Volume volume = volume(barSeries);
        cumulativeNumerator = cumulativeSum(multiply(closeLocationValue(barSeries), volume), length);
        cumulativeVolume = cumulativeSum(volume, length);
    }

    @Override
    protected Num calculate(long index) {
        return cumulativeNumerator.getValue(index).divide(cumulativeVolume.getValue(index));
    }
}
