package trade.invision.indicators.indicator.ao;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.indicators.indicator.macd.MovingAverageConvergenceDivergence;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.barprice.Hl2.hl2;

/**
 * {@link AwesomeOscillator} is a {@link Num} {@link Indicator} to provide the Awesome Oscillator (AO) over a
 * <code>shortLength</code> and <code>longLength</code> of {@link Bar}s.
 *
 * @see <a href="https://www.tradingview.com/support/solutions/43000501826-awesome-oscillator-ao/">TradingView</a>
 */
public class AwesomeOscillator extends MovingAverageConvergenceDivergence {

    /**
     * @see #awesomeOscillator(BarSeries, int, int, MovingAverageSupplier)
     */
    public static AwesomeOscillator ao(BarSeries barSeries, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        return awesomeOscillator(barSeries, shortLength, longLength, movingAverageSupplier);
    }

    /**
     * Gets a {@link AwesomeOscillator}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param shortLength           the short averaging length (typically 5)
     * @param longLength            the long averaging length (typically 34)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static AwesomeOscillator awesomeOscillator(BarSeries barSeries, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(barSeries, shortLength, longLength, movingAverageSupplier),
                key -> new AwesomeOscillator(barSeries, shortLength, longLength, movingAverageSupplier));
    }

    private static final Cache<CacheKey, AwesomeOscillator> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        int shortLength;
        int longLength;
        MovingAverageSupplier movingAverageSupplier;
    }

    protected AwesomeOscillator(BarSeries barSeries, int shortLength, int longLength,
            MovingAverageSupplier movingAverageSupplier) {
        super(hl2(barSeries), shortLength, longLength, movingAverageSupplier);
    }
}
