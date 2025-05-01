package trade.invision.indicators.indicator.ad;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.RecursiveIndicator;
import trade.invision.indicators.indicator.bar.Volume;
import trade.invision.indicators.indicator.clv.CloseLocationValue;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.bar.Volume.volume;
import static trade.invision.indicators.indicator.clv.CloseLocationValue.closeLocationValue;

/**
 * {@link AccumulationDistribution} is a {@link Num} {@link Indicator} to provide the Accumulation/Distribution (AD) of
 * a {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/a/accumulationdistribution.asp">Investopedia</a>
 */
public class AccumulationDistribution extends RecursiveIndicator<Num> {

    /**
     * @see #accumulationDistribution(BarSeries)
     */
    public static AccumulationDistribution ad(BarSeries barSeries) {
        return accumulationDistribution(barSeries);
    }

    /**
     * Gets a {@link AccumulationDistribution}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static AccumulationDistribution accumulationDistribution(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new AccumulationDistribution(barSeries));
    }

    private static final Cache<CacheKey, AccumulationDistribution> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final CloseLocationValue clv;
    private final Volume volume;

    protected AccumulationDistribution(BarSeries barSeries) {
        super(barSeries, 1);
        clv = closeLocationValue(barSeries);
        volume = volume(barSeries);
    }

    @Override
    protected Num calculate(long index) {
        final Num mfv = clv.getValue(index).multiply(volume.getValue(index));
        if (index == 0) {
            return mfv;
        }
        return mfv.add(getValue(index - 1));
    }
}
