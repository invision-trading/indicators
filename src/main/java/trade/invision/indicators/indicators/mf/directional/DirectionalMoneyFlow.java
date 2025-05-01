package trade.invision.indicators.indicators.mf.directional;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.barprice.Hlc3;
import trade.invision.indicators.indicators.mf.directional.DirectionalMoneyFlowResult.DirectionalMoneyFlowResultBuilder;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicators.bar.Volume.volume;
import static trade.invision.indicators.indicators.barprice.Hlc3.hlc3;

/**
 * {@link DirectionalMoneyFlow} is a {@link Num} {@link Indicator} to provide the Directional Money Flow (DMF) of a
 * {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/m/moneyflow.asp">Investopedia</a>
 */
public class DirectionalMoneyFlow extends Indicator<DirectionalMoneyFlowResult> {

    /**
     * @see #directionalMoneyFlow(BarSeries)
     */
    public static DirectionalMoneyFlow dmf(BarSeries barSeries) {
        return directionalMoneyFlow(barSeries);
    }

    /**
     * Gets a {@link DirectionalMoneyFlow}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static DirectionalMoneyFlow directionalMoneyFlow(BarSeries barSeries) {
        return CACHE.get(new CacheKey(barSeries), key -> new DirectionalMoneyFlow(barSeries));
    }

    private static final Cache<CacheKey, DirectionalMoneyFlow> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        BarSeries barSeries;
    }

    private final Hlc3 hlc3;
    private final Volume volume;

    protected DirectionalMoneyFlow(BarSeries barSeries) {
        super(barSeries, 1);
        hlc3 = hlc3(barSeries).caching();
        volume = volume(barSeries);
    }

    @Override
    protected DirectionalMoneyFlowResult calculate(long index) {
        final DirectionalMoneyFlowResultBuilder result = DirectionalMoneyFlowResult.builder()
                .positive(numOfZero())
                .negative(numOfZero());
        if (index == 0) {
            return result.build();
        }
        final Num previousHlc3Value = hlc3.getValue(index - 1);
        final Num hlc3Value = hlc3.getValue(index);
        final Num moneyFlow = hlc3Value.multiply(volume.getValue(index));
        if (hlc3Value.isGreaterThan(previousHlc3Value)) {
            result.positive(moneyFlow);
        } else if (hlc3Value.isLessThan(previousHlc3Value)) {
            result.negative(moneyFlow);
        }
        return result.build();
    }
}
