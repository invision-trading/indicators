package trade.invision.indicators.indicator.meta.series;

import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.constant.ConstantValue;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link GetEpsilon} is a {@link Num} {@link Indicator} to provide the {@link Series#getEpsilon()} of the given
 * {@link Series}.
 */
public class GetEpsilon extends ConstantValue<Num> {

    /**
     * Gets a {@link GetEpsilon}.
     *
     * @param series the {@link #getSeries()}
     */
    public static GetEpsilon getEpsilon(Series<?> series) {
        return new GetEpsilon(series);
    }

    protected GetEpsilon(Series<?> series) {
        super(series, series.getEpsilon());
    }
}
