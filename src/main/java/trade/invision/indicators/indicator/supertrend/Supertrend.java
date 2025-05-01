package trade.invision.indicators.indicator.supertrend;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.RecursiveIndicator;
import trade.invision.indicators.indicator.bar.Close;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicator.bar.Close.close;
import static trade.invision.indicators.indicator.supertrend.SupertrendLowerBand.supertrendLowerBand;
import static trade.invision.indicators.indicator.supertrend.SupertrendUpperBand.supertrendUpperBand;

/**
 * {@link Supertrend} is a {@link Num} {@link Indicator} to provide the Supertrend over a <code>length</code> of
 * {@link Bar}s.
 *
 * @see <a href="https://www.tradingview.com/support/solutions/43000634738-supertrend/">TradingView</a>
 */
public class Supertrend extends RecursiveIndicator<Num> {

    /**
     * Gets a {@link Supertrend}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param length                the number of values to look back at (typically 10)
     * @param multiplier            the multiplier (typically 3)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static Supertrend supertrend(BarSeries barSeries, int length, Num multiplier,
            MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(barSeries, length, multiplier, movingAverageSupplier),
                key -> new Supertrend(barSeries, length, multiplier, movingAverageSupplier));
    }

    private static final Cache<CacheKey, Supertrend> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int length;
        Num multiplier;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final Close close;
    private final SupertrendLowerBand lowerBand;
    private final SupertrendUpperBand upperBand;

    protected Supertrend(BarSeries barSeries, int length, Num multiplier, MovingAverageSupplier movingAverageSupplier) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        close = close(barSeries);
        lowerBand = supertrendLowerBand(barSeries, length, multiplier, movingAverageSupplier);
        upperBand = supertrendUpperBand(barSeries, length, multiplier, movingAverageSupplier);
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return numOfZero();
        }
        final Num closePrice = close.getValue(index);
        final Num previousValue = getValue(index - 1);
        final Num lower = lowerBand.getValue(index);
        final Num upper = upperBand.getValue(index);
        if (previousValue.equals(upperBand.getValue(index - 1))) {
            return closePrice.isGreaterThan(upper) ? lower : upper;
        } else if (previousValue.equals(lowerBand.getValue(index - 1))) {
            return closePrice.isLessThan(lower) ? upper : lower;
        } else {
            return previousValue;
        }
    }
}
