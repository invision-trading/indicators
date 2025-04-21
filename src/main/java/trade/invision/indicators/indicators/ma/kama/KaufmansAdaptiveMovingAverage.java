package trade.invision.indicators.indicators.ma.kama;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.RecursiveIndicator;
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
     * Convenience static method for {@link #KaufmansAdaptiveMovingAverage(Indicator, int)}.
     */
    public static KaufmansAdaptiveMovingAverage kaufmansAdaptiveMovingAverage(Indicator<Num> indicator,
            int efficiencyRatioLength) {
        return new KaufmansAdaptiveMovingAverage(indicator, efficiencyRatioLength);
    }

    /**
     * @see #kaufmansAdaptiveMovingAverage(Indicator, int, int, int)
     */
    public static KaufmansAdaptiveMovingAverage kama(Indicator<Num> indicator,
            int efficiencyRatioLength, int fastLength, int slowLength) {
        return kaufmansAdaptiveMovingAverage(indicator, efficiencyRatioLength, fastLength, slowLength);
    }

    /**
     * Convenience static method for {@link #KaufmansAdaptiveMovingAverage(Indicator, int, int, int)}.
     */
    public static KaufmansAdaptiveMovingAverage kaufmansAdaptiveMovingAverage(Indicator<Num> indicator,
            int efficiencyRatioLength, int fastLength, int slowLength) {
        return new KaufmansAdaptiveMovingAverage(indicator, efficiencyRatioLength, fastLength, slowLength);
    }

    private final Indicator<Num> indicator;
    private final int efficiencyRatioLength;
    private final Num fastest;
    private final Num slowest;

    /**
     * Instantiates a new {@link KaufmansAdaptiveMovingAverage} with <code>fastLength</code> and <code>slowLength</code>
     * set to typical values that are proportional with <code>efficiencyRatioLength</code>.
     *
     * @param indicator             the {@link Indicator}
     * @param efficiencyRatioLength the length of the efficiency ratio
     */
    public KaufmansAdaptiveMovingAverage(Indicator<Num> indicator, int efficiencyRatioLength) {
        this(indicator, efficiencyRatioLength,
                max(1, (int) rint(2.0 * (efficiencyRatioLength / 10.0))),
                max(1, (int) rint(30.0 * (efficiencyRatioLength / 10.0))));
    }

    /**
     * Instantiates a new {@link KaufmansAdaptiveMovingAverage}.
     *
     * @param indicator             the {@link Indicator}
     * @param efficiencyRatioLength the length of the efficiency ratio (typically 10)
     * @param fastLength            the length of the fast timeframe (typically 2)
     * @param slowLength            the length of the slow timeframe (typically 30)
     */
    public KaufmansAdaptiveMovingAverage(Indicator<Num> indicator, int efficiencyRatioLength,
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
