package trade.invision.indicators.indicators.risingfalling.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.RecursiveIndicator;
import trade.invision.num.Num;

import java.util.function.BiPredicate;

/**
 * {@link AbstractGlobalRisingFallingPercentage} is an abstract {@link Num} {@link Indicator} for global rising/falling
 * percentage {@link Indicator}s.
 */
abstract class AbstractGlobalRisingFallingPercentage extends RecursiveIndicator<Num> {

    private final Indicator<Num> indicator;
    private final BiPredicate<Num, Num> predicate;

    /**
     * Instantiates a new {@link AbstractGlobalRisingFallingPercentage}.
     *
     * @param indicator the {@link Indicator}
     * @param rising    <code>true</code> for rising, <code>false</code> for falling
     */
    protected AbstractGlobalRisingFallingPercentage(Indicator<Num> indicator, boolean rising) {
        super(indicator.getSeries(), 1);
        this.indicator = indicator.caching();
        predicate = rising ? Num::isGreaterThan : Num::isLessThan;
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return numOfZero();
        }
        final long previousIndex = index - 1;
        final Num previousValue = getValue(previousIndex);
        Num count = previousValue.multiply(previousIndex);
        if (predicate.test(indicator.getValue(index), indicator.getValue(previousIndex))) {
            count = count.increment();
        }
        return count.divide(index);
    }
}
