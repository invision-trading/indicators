package trade.invision.indicators.indicators.rb;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link RealBody} is a {@link Num} {@link Indicator} to provide the Real Body (RB) of a {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/r/realbody.asp">Investopedia</a>
 */
public class RealBody extends Indicator<Num> {

    /**
     * @see #realBody(BarSeries)
     */
    public static RealBody rb(BarSeries barSeries) {
        return realBody(barSeries);
    }

    /**
     * Gets a {@link RealBody}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static RealBody realBody(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new RealBody(barSeries));
    }

    private static final Cache<CacheKey, RealBody> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final BarSeries barSeries;

    protected RealBody(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return bar.getClose().subtract(bar.getOpen());
    }
}
