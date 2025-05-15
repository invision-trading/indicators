package trade.invision.indicators.indicators.vwap;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.cumulative.CumulativeSum;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.bar.Volume.volume;
import static trade.invision.indicators.indicators.cumulative.CumulativeSum.cumulativeSum;
import static trade.invision.indicators.indicators.mf.MoneyFlow.moneyFlow;

/**
 * {@link VolumeWeightedAveragePrice} is a {@link Num} {@link Indicator} to provide the Volume-Weighted Average Price
 * (VWAP) over a <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.investopedia.com/terms/v/vwap.asp">Investopedia</a>
 */
public class VolumeWeightedAveragePrice extends Indicator<Num> {

    /**
     * @see #volumeWeightedAveragePrice(BarSeries, int)
     */
    public static VolumeWeightedAveragePrice vwap(BarSeries barSeries, int length) {
        return volumeWeightedAveragePrice(barSeries, length);
    }

    /**
     * Gets a {@link VolumeWeightedAveragePrice}.
     *
     * @param barSeries the {@link BarSeries}
     * @param length    the number of values to look back at
     */
    public static VolumeWeightedAveragePrice volumeWeightedAveragePrice(BarSeries barSeries, int length) {
        return CACHE.get(new CacheKey(barSeries, length), key -> new VolumeWeightedAveragePrice(barSeries, length));
    }

    private static final Cache<CacheKey, VolumeWeightedAveragePrice> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int length;
    }

    private final CumulativeSum cumulativeMoneyFlow;
    private final CumulativeSum cumulativeVolume;

    protected VolumeWeightedAveragePrice(BarSeries barSeries, int length) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        cumulativeMoneyFlow = cumulativeSum(moneyFlow(barSeries), length);
        cumulativeVolume = cumulativeSum(volume(barSeries), length);
    }

    @Override
    protected Num calculate(long index) {
        return cumulativeMoneyFlow.getValue(index).divide(cumulativeVolume.getValue(index));
    }
}
