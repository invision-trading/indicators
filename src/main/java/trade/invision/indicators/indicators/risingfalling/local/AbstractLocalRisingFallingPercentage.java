package trade.invision.indicators.indicators.risingfalling.local;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import java.util.function.BiPredicate;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link AbstractLocalRisingFallingPercentage} is an abstract {@link Num} {@link Indicator} for local rising/falling
 * percentage {@link Indicator}s.
 */
abstract class AbstractLocalRisingFallingPercentage extends Indicator<Num> {

    private final Indicator<Num> indicator;
    private final int length;
    private final BiPredicate<Num, Num> predicate;

    /**
     * Instantiates a new {@link AbstractLocalRisingFallingPercentage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param rising    <code>true</code> for rising, <code>false</code> for falling
     */
    public AbstractLocalRisingFallingPercentage(Indicator<Num> indicator, int length, boolean rising) {
        super(indicator.getSeries(), 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
        predicate = rising ? Num::isGreaterThan : Num::isLessThan;
    }

    @Override
    protected Num calculate(long index) {
        // TODO this can be optimized similar to 'CumulativeSum'.
        final long startIndex = max(1, index - length + 1);
        final long observations = index - startIndex + 1;
        int count = 0;
        for (long indicatorIndex = startIndex; indicatorIndex <= index; indicatorIndex++) {
            if (predicate.test(indicator.getValue(indicatorIndex), indicator.getValue(indicatorIndex - 1))) {
                count++;
            }
        }
        return numOf(count).divide(observations);
    }
}
