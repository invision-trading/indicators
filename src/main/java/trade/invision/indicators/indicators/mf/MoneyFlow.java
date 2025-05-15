package trade.invision.indicators.indicators.mf;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.barprice.Hlc3;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicators.bar.Volume.volume;
import static trade.invision.indicators.indicators.barprice.Hlc3.hlc3;

/**
 * {@link MoneyFlow} is a {@link Num} {@link Indicator} to provide the Money Flow (MF) of a {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/m/moneyflow.asp">Investopedia</a>
 */
public class MoneyFlow extends Indicator<Num> {

    /**
     * @see #moneyFlow(BarSeries)
     */
    public static MoneyFlow mf(BarSeries barSeries) {
        return moneyFlow(barSeries);
    }

    /**
     * Gets a {@link MoneyFlow}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static MoneyFlow moneyFlow(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new MoneyFlow(barSeries));
    }

    private static final Cache<CacheKey, MoneyFlow> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final Hlc3 hlc3;
    private final Volume volume;

    protected MoneyFlow(BarSeries barSeries) {
        super(barSeries, 0);
        hlc3 = hlc3(barSeries);
        volume = volume(barSeries);
    }

    @Override
    protected Num calculate(long index) {
        return hlc3.getValue(index).multiply(volume.getValue(index));
    }
}
