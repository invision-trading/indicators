package trade.invision.indicators.indicator.barprice;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link Hlc3} is a {@link Num} {@link Indicator} to provide the HLC/3 price of a {@link Bar}. This is also known as
 * the "Typical Price"
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=TypicalPrices.htm">FM Labs</a>
 */
public class Hlc3 extends Indicator<Num> {

    /**
     * @see #hlc3(BarSeries)
     */
    public static Hlc3 typicalPrice(BarSeries barSeries) {
        return hlc3(barSeries);
    }

    /**
     * Gets a {@link Hlc3}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static Hlc3 hlc3(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new Hlc3(barSeries));
    }

    private static final Cache<CacheKey, Hlc3> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final BarSeries barSeries;

    protected Hlc3(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return bar.getHigh().add(bar.getLow()).add(bar.getClose()).divide(numOfThree());
    }
}
