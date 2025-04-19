package trade.invision.indicators.indicators.extrema.local;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import java.util.function.BiPredicate;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link AbstractLocalExtrema} is an abstract {@link Num} {@link Indicator} for the local extrema {@link Indicator}s.
 */
abstract class AbstractLocalExtrema extends Indicator<Num> {

    private final Indicator<Num> indicator;
    private final int length;
    private final BiPredicate<Num, Num> predicate;

    /**
     * Instantiates a new {@link AbstractLocalExtrema}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     * @param maximum   <code>true</code> for {@link Num#maximum(Num)}, <code>false</code> for {@link Num#minimum(Num)}
     */
    public AbstractLocalExtrema(Indicator<Num> indicator, int length, boolean maximum) {
        super(indicator.getSeries(), 0);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
        predicate = maximum ? Num::isGreaterThan : Num::isLessThan;
    }

    @Override
    protected Num calculate(long index) {
        // TODO this can be optimized similar to 'CumulativeSum'.
        Num extrema = indicator.getValue(index);
        final long endIndex = max(0, index - length + 1);
        for (long indicatorIndex = index - 1; indicatorIndex >= endIndex; indicatorIndex--) {
            final Num value = indicator.getValue(indicatorIndex);
            if (predicate.test(value, extrema)) {
                extrema = value;
            }
        }
        return extrema;
    }
}
