package trade.invision.indicators.indicators.clv;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link CloseLocationValue} is a {@link Num} {@link Indicator} to provide the Close Location Value (CLV) of a
 * {@link Bar}.
 *
 * @see <a href="http://www.investopedia.com/terms/c/close_location_value.asp">Investopedia</a>
 */
public class CloseLocationValue extends Indicator<Num> {

    /**
     * @see #closeLocationValue(BarSeries)
     */
    public static CloseLocationValue clv(BarSeries barSeries) {
        return closeLocationValue(barSeries);
    }

    /**
     * Gets a {@link CloseLocationValue}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static CloseLocationValue closeLocationValue(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new CloseLocationValue(barSeries));
    }

    private static final Cache<CacheKey, CloseLocationValue> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final BarSeries barSeries;

    protected CloseLocationValue(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        final Num low = bar.getLow();
        final Num high = bar.getHigh();
        final Num close = bar.getClose();
        return close.subtract(low).subtract(high.subtract(close)).divide(high.subtract(low));
    }
}
