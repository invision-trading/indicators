package trade.invision.indicators.indicator.ma.kama;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.RecursiveIndicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.Math.rint;

/**
 * {@link KaufmansAdaptiveMovingAverage} is a {@link Num} {@link Indicator} to provide a Kaufman's Adaptive Moving
 * Average (KAMA) over a <code>efficiencyRatioLength</code> of values.
 *
 * @see <a
 * href="https://chartschool.stockcharts.com/table-of-contents/technical-indicators-and-overlays/technical-overlays/kaufmans-adaptive-moving-average-kama">StockCharts</a>
 */
public class KaufmansAdaptiveMovingAverage extends RecursiveIndicator<Num> {

    /**
     * @see #kaufmansAdaptiveMovingAverage(Indicator, int)
     */
    public static KaufmansAdaptiveMovingAverage kama(Indicator<Num> indicator, int efficiencyRatioLength) {
        return kaufmansAdaptiveMovingAverage(indicator, efficiencyRatioLength);
    }

    /**
     * Calls {@link #kaufmansAdaptiveMovingAverage(Indicator, int, int, int)} with <code>fastLength</code> and
     * <code>slowLength</code> set to typical values that are proportional with <code>efficiencyRatioLength</code>.
     */
    public static KaufmansAdaptiveMovingAverage kaufmansAdaptiveMovingAverage(Indicator<Num> indicator,
            int efficiencyRatioLength) {
        return kaufmansAdaptiveMovingAverage(indicator, efficiencyRatioLength,
                max(1, (int) rint(2.0 * (efficiencyRatioLength / 10.0))),
                max(1, (int) rint(30.0 * (efficiencyRatioLength / 10.0))));
    }

    /**
     * @see #kaufmansAdaptiveMovingAverage(Indicator, int, int, int)
     */
    public static KaufmansAdaptiveMovingAverage kama(Indicator<Num> indicator,
            int efficiencyRatioLength, int fastLength, int slowLength) {
        return kaufmansAdaptiveMovingAverage(indicator, efficiencyRatioLength, fastLength, slowLength);
    }

    /**
     * Gets a {@link KaufmansAdaptiveMovingAverage}.
     *
     * @param indicator             the {@link Indicator}
     * @param efficiencyRatioLength the length of the efficiency ratio (typically 10)
     * @param fastLength            the length of the fast timeframe (typically 2)
     * @param slowLength            the length of the slow timeframe (typically 30)
     */
    public static KaufmansAdaptiveMovingAverage kaufmansAdaptiveMovingAverage(Indicator<Num> indicator,
            int efficiencyRatioLength, int fastLength, int slowLength) {
        return CACHE.get(new CacheKey(indicator, efficiencyRatioLength, fastLength, slowLength),
                key -> new KaufmansAdaptiveMovingAverage(indicator, efficiencyRatioLength, fastLength, slowLength));
    }

    private static final Cache<CacheKey, KaufmansAdaptiveMovingAverage> CACHE =
            Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int efficiencyRatioLength;
        int fastLength;
        int slowLength;
    }

    private final Indicator<Num> indicator;
    private final int efficiencyRatioLength;
    private final Num fastest;
    private final Num slowest;

    protected KaufmansAdaptiveMovingAverage(Indicator<Num> indicator, int efficiencyRatioLength,
            int fastLength, int slowLength) {
        super(indicator.getSeries(), max(efficiencyRatioLength, max(fastLength, slowLength)));
        checkArgument(efficiencyRatioLength > 0, "'efficiencyRatioLength' must be greater than zero!");
        checkArgument(fastLength > 0, "'fastLength' must be greater than zero!");
        checkArgument(slowLength > 0, "'slowLength' must be greater than zero!");
        this.indicator = indicator.caching();
        this.efficiencyRatioLength = efficiencyRatioLength;
        fastest = numOfTwo().divide(fastLength + 1);
        slowest = numOfTwo().divide(slowLength + 1);
    }

    @Override
    protected Num calculate(long index) {
        final Num currentValue = indicator.getValue(index);
        if (index < efficiencyRatioLength) {
            return currentValue;
        }
        final long startChangeIndex = max(0, index - efficiencyRatioLength);
        final Num change = currentValue.subtract(indicator.getValue(startChangeIndex)).absoluteValue();
        Num volatility = numOfZero();
        for (long indicatorIndex = startChangeIndex; indicatorIndex < index; indicatorIndex++) {
            volatility = volatility.add(indicator.getValue(indicatorIndex + 1)
                    .subtract(indicator.getValue(indicatorIndex)).absoluteValue());
        }
        final Num er = change.divide(volatility);
        final Num scInner = er.multiply(fastest.subtract(slowest)).add(slowest);
        final Num previousValue = getValue(index - 1);
        return previousValue.add(scInner.square().multiply(currentValue.subtract(previousValue)));
    }
}
