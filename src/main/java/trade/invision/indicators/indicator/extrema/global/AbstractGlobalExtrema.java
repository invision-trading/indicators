package trade.invision.indicators.indicator.extrema.global;

import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.RecursiveIndicator;
import trade.invision.num.Num;

import java.util.function.BiPredicate;

/**
 * {@link AbstractGlobalExtrema} is an abstract {@link Num} {@link Indicator} for the global extrema
 * {@link Indicator}s.
 */
abstract class AbstractGlobalExtrema extends RecursiveIndicator<Num> {

    private final Indicator<Num> indicator;
    private final BiPredicate<Num, Num> predicate;

    /**
     * Instantiates a new {@link AbstractGlobalExtrema}.
     *
     * @param indicator the {@link Indicator}
     * @param maximum   <code>true</code> for {@link Num#maximum(Num)}, <code>false</code> for {@link Num#minimum(Num)}
     */
    protected AbstractGlobalExtrema(Indicator<Num> indicator, boolean maximum) {
        super(indicator.getSeries(), 0);
        this.indicator = indicator;
        predicate = maximum ? Num::isGreaterThan : Num::isLessThan;
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return indicator.getValue(index);
        }
        final Num currentValue = indicator.getValue(index);
        final Num previousValue = getValue(index - 1);
        return predicate.test(currentValue, previousValue) ? currentValue : previousValue;
    }
}
