package trade.invision.indicators.indicators.rb;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link AbsoluteRealBody} is a {@link Num} {@link Indicator} to provide the absolute value of the Real Body (ARB) of a
 * {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/r/realbody.asp">Investopedia</a>
 */
public class AbsoluteRealBody extends RealBody {

    /**
     * @see #absoluteRealBody(BarSeries)
     */
    public static AbsoluteRealBody arb(BarSeries barSeries) {
        return absoluteRealBody(barSeries);
    }

    /**
     * Gets a {@link AbsoluteRealBody}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static AbsoluteRealBody absoluteRealBody(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new AbsoluteRealBody(barSeries));
    }

    private static final Cache<CacheKey, AbsoluteRealBody> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    protected AbsoluteRealBody(BarSeries barSeries) {
        super(barSeries);
    }

    @Override
    protected Num calculate(long index) {
        return super.calculate(index).absoluteValue();
    }
}
