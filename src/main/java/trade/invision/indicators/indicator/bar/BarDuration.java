package trade.invision.indicators.indicator.bar;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import java.time.temporal.ChronoUnit;

import static java.time.ZoneOffset.UTC;

/**
 * {@link BarDuration} is a {@link Num} {@link Indicator} to provide {@link Bar#getDuration()} in a specified
 * {@link ChronoUnit}.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class BarDuration extends Indicator<Num> {

    /**
     * Gets a {@link BarDuration}.
     *
     * @param barSeries the {@link BarSeries}
     * @param unit      the {@link ChronoUnit}
     */
    public static BarDuration barDuration(BarSeries barSeries, ChronoUnit unit) {
        return CACHE.get(new CacheKey(barSeries, unit), key -> new BarDuration(barSeries, unit));
    }

    private static final Cache<CacheKey, BarDuration> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        ChronoUnit unit;
    }

    private final BarSeries barSeries;
    private final ChronoUnit unit;

    protected BarDuration(BarSeries barSeries, ChronoUnit unit) {
        super(barSeries, 0);
        this.barSeries = barSeries;
        this.unit = unit;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return numOf(unit.between(bar.getEnd().atZone(UTC), bar.getStart().atZone(UTC)));
    }
}
