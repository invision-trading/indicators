package trade.invision.indicators.indicator.obv;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.RecursiveIndicator;
import trade.invision.indicators.indicator.bar.Close;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.bar.Close.close;
import static trade.invision.indicators.indicator.bar.Volume.volume;
import static trade.invision.indicators.indicator.previous.PreviousValue.previousValue;

/**
 * {@link OnBalanceVolume} is a {@link Num} {@link Indicator} to provide the On-Balance Volume (OBV).
 *
 * @see <a href="https://www.investopedia.com/terms/o/onbalancevolume.asp">Investopedia</a>
 */
public class OnBalanceVolume extends RecursiveIndicator<Num> {

    /**
     * @see #onBalanceVolume(BarSeries)
     */
    public static OnBalanceVolume obv(BarSeries barSeries) {
        return onBalanceVolume(barSeries);
    }

    /**
     * Gets a {@link OnBalanceVolume}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static OnBalanceVolume onBalanceVolume(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new OnBalanceVolume(barSeries));
    }

    private static final Cache<CacheKey, OnBalanceVolume> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final Close close;
    private final Indicator<Num> previousClose;
    private final Indicator<Num> volume;

    protected OnBalanceVolume(BarSeries barSeries) {
        super(barSeries, 1);
        volume = volume(barSeries);
        close = close(barSeries);
        previousClose = previousValue(close);
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return numOfZero();
        }
        final Num previousCloseValue = previousClose.getValue(index);
        final Num closeValue = close.getValue(index);
        Num previousValue = getValue(index - 1);
        if (closeValue.isGreaterThan(previousCloseValue)) {
            previousValue = previousValue.add(volume.getValue(index));
        } else if (closeValue.isLessThan(previousCloseValue)) {
            previousValue = previousValue.subtract(volume.getValue(index));
        }
        return previousValue;
    }
}
