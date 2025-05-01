package trade.invision.indicators.indicator.closeprice;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.bar.Close;
import trade.invision.indicators.indicator.previous.PreviousRatio;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.bar.Close.close;

/**
 * {@link ClosePriceRatio} is a {@link Num} {@link Indicator} to use {@link PreviousRatio} with {@link Close}.
 */
public class ClosePriceRatio extends PreviousRatio {

    /**
     * Calls {@link #closePriceRatio(BarSeries, int)} with <code>n</code> set to <code>1</code>.
     */
    public static ClosePriceRatio closePriceRatio(BarSeries barSeries) {
        return closePriceRatio(barSeries, 1);
    }

    /**
     * Gets a {@link ClosePriceRatio}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static ClosePriceRatio closePriceRatio(BarSeries barSeries, int n) {
        return CACHE.get(new CacheKey(barSeries, n), key -> new ClosePriceRatio(barSeries, n));
    }

    private static final Cache<CacheKey, ClosePriceRatio> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int n;
    }

    protected ClosePriceRatio(BarSeries barSeries, int n) {
        super(close(barSeries), n);
    }
}
