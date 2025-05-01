package trade.invision.indicators.indicator.mf.directional;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.bar.Volume;
import trade.invision.indicators.indicator.barprice.Hlc3;
import trade.invision.indicators.indicator.mf.directional.DirectionalMoneyFlowResult.DirectionalMoneyFlowResultBuilder;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.bar.Volume.volume;
import static trade.invision.indicators.indicator.barprice.Hlc3.hlc3;
import static trade.invision.indicators.indicator.operation.unary.UnaryOperation.unaryOperation;

/**
 * {@link DirectionalMoneyFlow} is a {@link Num} {@link Indicator} to provide the Directional Money Flow (DMF) of a
 * {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/m/moneyflow.asp">Investopedia</a>
 */
public class DirectionalMoneyFlow extends Indicator<DirectionalMoneyFlowResult> {

    /**
     * @see #positiveDirectionalMoneyFlow(BarSeries)
     */
    public static Indicator<Num> positiveDmf(BarSeries barSeries) {
        return positiveDirectionalMoneyFlow(barSeries);
    }

    /**
     * Gets {@link DirectionalMoneyFlowResult#getPositive()} from {@link #directionalMoneyFlow(BarSeries)}.
     */
    public static Indicator<Num> positiveDirectionalMoneyFlow(BarSeries barSeries) {
        return unaryOperation(DirectionalMoneyFlowResult::getPositive, directionalMoneyFlow(barSeries));
    }

    /**
     * @see #negativeDirectionalMoneyFlow(BarSeries)
     */
    public static Indicator<Num> negativeDmf(BarSeries barSeries) {
        return negativeDirectionalMoneyFlow(barSeries);
    }

    /**
     * Gets {@link DirectionalMoneyFlowResult#getNegative()} from {@link #directionalMoneyFlow(BarSeries)}.
     */
    public static Indicator<Num> negativeDirectionalMoneyFlow(BarSeries barSeries) {
        return unaryOperation(DirectionalMoneyFlowResult::getNegative, directionalMoneyFlow(barSeries));
    }

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
