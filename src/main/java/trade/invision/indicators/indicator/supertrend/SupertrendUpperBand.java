package trade.invision.indicators.indicator.supertrend;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.RecursiveIndicator;
import trade.invision.indicators.indicator.barprice.Hl2;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.indicators.indicator.previous.PreviousValue;
import trade.invision.indicators.indicator.tr.AverageTrueRange;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.bar.Close.close;
import static trade.invision.indicators.indicator.barprice.Hl2.hl2;
import static trade.invision.indicators.indicator.previous.PreviousValue.previousValue;
import static trade.invision.indicators.indicator.tr.AverageTrueRange.averageTrueRange;

/**
 * {@link SupertrendUpperBand} is a {@link Num} {@link Indicator} to provide the upper band of a Supertrend over a
 * <code>length</code> of values.
 *
 * @see <a href="https://www.tradingview.com/support/solutions/43000634738-supertrend/">TradingView</a>
 */
public class SupertrendUpperBand extends RecursiveIndicator<Num> {

    /**
     * Gets a {@link SupertrendUpperBand}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param length                the number of values to look back at (typically 10)
     * @param multiplier            the multiplier (typically 3)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static SupertrendUpperBand supertrendUpperBand(BarSeries barSeries, int length, Num multiplier,
            MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(barSeries, length, multiplier, movingAverageSupplier),
                key -> new SupertrendUpperBand(barSeries, length, multiplier, movingAverageSupplier));
    }

    private static final Cache<CacheKey, SupertrendUpperBand> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int length;
        Num multiplier;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final Num multiplier;
    private final AverageTrueRange atr;
    private final Hl2 hl2;
    private final PreviousValue<Num> previousClose;

    protected SupertrendUpperBand(BarSeries barSeries, int length, Num multiplier,
            MovingAverageSupplier movingAverageSupplier) {
        super(barSeries, length - 1);
        this.multiplier = multiplier;
        this.atr = averageTrueRange(barSeries, length, movingAverageSupplier);
        this.hl2 = hl2(barSeries);
        this.previousClose = previousValue(close(barSeries));
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return numOfZero();
        }
        final Num currentBasic = hl2.getValue(index).add(multiplier.multiply(atr.getValue(index)));
        final Num previousValue = getValue(index - 1);
        final Num previousClosePrice = previousClose.getValue(index);
        return currentBasic.isLessThan(previousValue) || previousClosePrice.isGreaterThan(previousValue) ?
                currentBasic : previousValue;
    }
}
