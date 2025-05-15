package trade.invision.indicators.indicators.closeprice;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Close;
import trade.invision.indicators.indicators.previous.PreviousDifference;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicators.bar.Close.close;

/**
 * {@link ClosePriceDifference} is a {@link Num} {@link Indicator} to use {@link PreviousDifference} with
 * {@link Close}.
 */
public class ClosePriceDifference extends PreviousDifference {

    /**
     * Calls {@link #closePriceDifference(BarSeries, int)} with <code>n</code> set to <code>1</code>.
     */
    public static ClosePriceDifference closePriceDifference(BarSeries barSeries) {
        return closePriceDifference(barSeries, 1);
    }

    /**
     * Gets a {@link ClosePriceDifference}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static ClosePriceDifference closePriceDifference(BarSeries barSeries, int n) {
        return CACHE.get(new CacheKey(barSeries, n), key -> new ClosePriceDifference(barSeries, n));
    }

    private static final Cache<CacheKey, ClosePriceDifference> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int n;
    }

    protected ClosePriceDifference(BarSeries barSeries, int n) {
        super(close(barSeries), n);
    }
}
