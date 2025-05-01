package trade.invision.indicators.indicators.bar;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;

/**
 * {@link Overlaps} is a {@link Boolean} {@link Indicator} to provide {@link Bar#overlaps(Bar)}.
 */
public class Overlaps extends Indicator<Boolean> {

    /**
     * Gets a {@link Overlaps}.
     *
     * @param barSeries the {@link BarSeries}
     * @param check     the check {@link BarSeries}
     */
    public static Overlaps overlaps(BarSeries barSeries, BarSeries check) {
        return CACHE.get(new CacheKey(barSeries, check), key -> new Overlaps(barSeries, check));
    }

    private static final Cache<CacheKey, Overlaps> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        BarSeries check;
    }

    private final BarSeries barSeries;
    private final BarSeries check;

    protected Overlaps(BarSeries barSeries, BarSeries check) {
        super(barSeries, 0);
        this.barSeries = barSeries;
        this.check = check;
    }

    @Override
    protected Boolean calculate(long index) {
        return barSeries.get(index).overlaps(check.get(index));
    }
}
