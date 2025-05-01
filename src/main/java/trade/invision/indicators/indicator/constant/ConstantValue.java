package trade.invision.indicators.indicator.constant;

import trade.invision.indicators.indicator.CachelessIndicator;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.Series;

/**
 * {@link ConstantValue} is an {@link Indicator} to provide a constant value for all indices.
 *
 * @param <T> the type
 */
public class ConstantValue<T> extends CachelessIndicator<T> {

    /**
     * Gets a {@link ConstantValue}.
     *
     * @param series        the {@link #getSeries()}
     * @param constantValue the constant value
     */
    public static <T> ConstantValue<T> constantValue(Series<?> series, T constantValue) {
        return new ConstantValue<>(series, constantValue);
    }

    private final T constantValue;

    protected ConstantValue(Series<?> series, T constantValue) {
        super(series, 0);
        this.constantValue = constantValue;
    }

    @Override
    protected T calculate(long index) {
        return constantValue;
    }
}
