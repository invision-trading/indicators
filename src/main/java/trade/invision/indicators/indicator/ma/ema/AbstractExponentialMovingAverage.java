package trade.invision.indicators.indicator.ma.ema;

import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.RecursiveIndicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link AbstractExponentialMovingAverage} is an abstract {@link Num} {@link Indicator} for an Exponential Moving
 * Average (EMA).
 */
abstract class AbstractExponentialMovingAverage extends RecursiveIndicator<Num> {

    private final Indicator<Num> indicator;
    private final Num multiplier;

    /**
     * Instantiates a new {@link AbstractExponentialMovingAverage}.
     *
     * @param indicator  the {@link Indicator}
     * @param length     the number of values to look back at
     * @param multiplier the multiplier
     */
    protected AbstractExponentialMovingAverage(Indicator<Num> indicator, int length, Num multiplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        this.multiplier = multiplier;
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return indicator.getValue(0);
        }
        final Num previousValue = getValue(index - 1);
        return indicator.getValue(index).subtract(previousValue).multiply(multiplier).add(previousValue);
    }
}
