package trade.invision.indicators.indicator.tr;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicator.tr.TrueRange.trueRange;

/**
 * {@link AverageTrueRange} is a {@link Num} {@link Indicator} to provide an Average True Range (ATR) over a
 * <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.investopedia.com/terms/a/atr.asp">Investopedia</a>
 */
public class AverageTrueRange extends Indicator<Num> {

    /**
     * @see #averageTrueRange(BarSeries, int, MovingAverageSupplier)
     */
    public static AverageTrueRange atr(BarSeries barSeries, int length, MovingAverageSupplier movingAverageSupplier) {
        return averageTrueRange(barSeries, length, movingAverageSupplier);
    }

    /**
     * Gets a {@link AverageTrueRange}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static AverageTrueRange averageTrueRange(BarSeries barSeries, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(barSeries, length, movingAverageSupplier),
                key -> new AverageTrueRange(barSeries, length, movingAverageSupplier));
    }

    private static final Cache<CacheKey, AverageTrueRange> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int length;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final Indicator<Num> movingAverage;

    protected AverageTrueRange(BarSeries barSeries, int length, MovingAverageSupplier movingAverageSupplier) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        movingAverage = movingAverageSupplier.supply(trueRange(barSeries), length);
    }

    @Override
    protected Num calculate(long index) {
        return movingAverage.getValue(index);
    }
}
