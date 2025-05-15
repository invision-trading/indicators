package trade.invision.indicators.indicators.iii;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link IntradayIntensityIndex} is a {@link Num} {@link Indicator} to provide the Intraday Intensity Index (III) of a
 * {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/i/intradayintensityindex.asp">Investopedia</a>
 */
public class IntradayIntensityIndex extends Indicator<Num> {

    /**
     * @see #intradayIntensityIndex(BarSeries)
     */
    public static IntradayIntensityIndex iii(BarSeries barSeries) {
        return intradayIntensityIndex(barSeries);
    }

    /**
     * Gets a {@link IntradayIntensityIndex}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static IntradayIntensityIndex intradayIntensityIndex(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new IntradayIntensityIndex(barSeries));
    }

    private static final Cache<CacheKey, IntradayIntensityIndex> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final BarSeries barSeries;

    protected IntradayIntensityIndex(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        final Num close = bar.getClose();
        final Num high = bar.getHigh();
        final Num low = bar.getLow();
        final Num volume = bar.getVolume();
        return numOfTwo().multiply(close).subtract(high).subtract(low).divide(high.subtract(low).multiply(volume));
    }
}
