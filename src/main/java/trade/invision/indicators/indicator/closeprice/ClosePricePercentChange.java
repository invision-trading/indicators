package trade.invision.indicators.indicator.closeprice;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.bar.Close;
import trade.invision.indicators.indicator.previous.PreviousPercentChange;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.bar.Close.close;

/**
 * {@link ClosePricePercentChange} is a {@link Num} {@link Indicator} to use {@link PreviousPercentChange} with
 * {@link Close}. This is also referred to as Rate of Change (ROC) or Momentum. The percentage is represented as a
 * fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 */
public class ClosePricePercentChange extends PreviousPercentChange {

    /**
     * Calls {@link #closePricePercentChange(BarSeries, int)} with <code>n</code> set to <code>1</code>.
     */
    public static ClosePricePercentChange closePricePercentChange(BarSeries barSeries) {
        return closePricePercentChange(barSeries, 1);
    }

    /**
     * Gets a {@link ClosePricePercentChange}.
     *
     * @param barSeries the {@link BarSeries}
     * @param n         the previous <i>n</i>-th value to look back at
     */
    public static ClosePricePercentChange closePricePercentChange(BarSeries barSeries, int n) {
        return CACHE.get(new CacheKey(barSeries, n), key -> new ClosePricePercentChange(barSeries, n));
    }

    private static final Cache<CacheKey, ClosePricePercentChange> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int n;
    }

    protected ClosePricePercentChange(BarSeries barSeries, int n) {
        super(close(barSeries), n);
    }
}
