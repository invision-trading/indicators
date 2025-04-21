package trade.invision.indicators.indicators.ma.ema;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.RecursiveIndicator;
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
    public AbstractExponentialMovingAverage(Indicator<Num> indicator, int length, Num multiplier) {
        super(indicator.getSeries(), length);
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
