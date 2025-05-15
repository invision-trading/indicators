package trade.invision.indicators.indicators.bar;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;

import java.time.Instant;

/**
 * {@link ContainsInstant} is a {@link Boolean} {@link Indicator} to provide {@link Bar#containsInstant(Instant)} for
 * {@link Instant}s.
 */
public class ContainsInstant extends Indicator<Boolean> {

    /**
     * Gets a {@link ContainsInstant}.
     *
     * @param barSeries the {@link BarSeries}
     * @param check     the check {@link Indicator}
     */
    public static ContainsInstant containsInstant(BarSeries barSeries, Indicator<Instant> check) {
        return CACHE.get(new CacheKey(barSeries, check), key -> new ContainsInstant(barSeries, check));
    }

    private static final Cache<CacheKey, ContainsInstant> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
        Indicator<Instant> check;
    }

    private final BarSeries barSeries;
    private final Indicator<Instant> check;

    protected ContainsInstant(BarSeries barSeries, Indicator<Instant> check) {
        super(barSeries, 0);
        this.barSeries = barSeries;
        this.check = check;
    }

    @Override
    protected Boolean calculate(long index) {
        return barSeries.get(index).containsInstant(check.getValue(index));
    }
}
