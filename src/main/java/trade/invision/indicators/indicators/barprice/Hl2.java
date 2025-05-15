package trade.invision.indicators.indicators.barprice;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link Hl2} is a {@link Num} {@link Indicator} to provide the HL/2 price of a {@link Bar}. This is also known as the
 * "Median Price"
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=MedianPrices.htm">FM Labs</a>
 */
public class Hl2 extends Indicator<Num> {

    /**
     * @see #hl2(BarSeries)
     */
    public static Hl2 medianPrice(BarSeries barSeries) {
        return hl2(barSeries);
    }

    /**
     * Gets a {@link Hl2}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static Hl2 hl2(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new Hl2(barSeries));
    }

    private static final Cache<CacheKey, Hl2> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final BarSeries barSeries;

    protected Hl2(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return bar.getHigh().add(bar.getLow()).divide(numOfTwo());
    }
}
