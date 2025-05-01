package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.constant.ConstantValue;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link GetMaximumLength} is a {@link Num} {@link Indicator} to provide the {@link Series#getMaximumLength()} of the
 * given {@link Series}.
 */
public class GetMaximumLength extends ConstantValue<Num> {

    /**
     * Gets a {@link GetMaximumLength}.
     *
     * @param series the {@link #getSeries()}
     */
    public static GetMaximumLength getMaximumLength(Series<?> series) {
        return new GetMaximumLength(series);
    }

    protected GetMaximumLength(Series<?> series) {
        super(series, series.getNumFactory().of(series.getMaximumLength()));
    }
}
