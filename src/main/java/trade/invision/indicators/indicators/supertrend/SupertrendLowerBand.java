package trade.invision.indicators.indicators.supertrend;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.RecursiveIndicator;
import trade.invision.indicators.indicators.barprice.Hl2;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.indicators.indicators.previous.PreviousValue;
import trade.invision.indicators.indicators.tr.AverageTrueRange;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicators.bar.Close.close;
import static trade.invision.indicators.indicators.barprice.Hl2.hl2;
import static trade.invision.indicators.indicators.previous.PreviousValue.previousValue;
import static trade.invision.indicators.indicators.tr.AverageTrueRange.averageTrueRange;

/**
 * {@link SupertrendLowerBand} is a {@link Num} {@link Indicator} to provide the lower band of a Supertrend over a
 * <code>length</code> of values.
 *
 * @see <a href="https://www.tradingview.com/support/solutions/43000634738-supertrend/">TradingView</a>
 */
public class SupertrendLowerBand extends RecursiveIndicator<Num> {

    /**
     * Gets a {@link SupertrendLowerBand}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param length                the number of values to look back at (typically 10)
     * @param multiplier            the multiplier (typically 3)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static SupertrendLowerBand supertrendLowerBand(BarSeries barSeries, int length, Num multiplier,
            MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(barSeries, length, multiplier, movingAverageSupplier),
                key -> new SupertrendLowerBand(barSeries, length, multiplier, movingAverageSupplier));
    }

    private static final Cache<CacheKey, SupertrendLowerBand> CACHE = Caffeine.newBuilder().weakValues().build();

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

    protected SupertrendLowerBand(BarSeries barSeries, int length, Num multiplier,
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
        final Num currentBasic = hl2.getValue(index).subtract(multiplier.multiply(atr.getValue(index)));
        final Num previousValue = getValue(index - 1);
        final Num previousClosePrice = previousClose.getValue(index);
        return currentBasic.isGreaterThan(previousValue) || previousClosePrice.isLessThan(previousValue) ?
                currentBasic : previousValue;
    }
}
