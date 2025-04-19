package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.constant.ConstantValue;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link GetEpsilon} is a {@link Num} {@link Indicator} to provide the {@link Series#getEpsilon()} of the given
 * {@link Series}.
 */
public class GetEpsilon extends ConstantValue<Num> {

    /**
     * Convenience static method for {@link #GetEpsilon(Series)}.
     */
    public static GetEpsilon getEpsilon(Series<?> series) {
        return new GetEpsilon(series);
    }

    /**
     * Instantiates a new {@link GetEpsilon}.
     *
     * @param series the {@link #getSeries()}
     */
    public GetEpsilon(Series<?> series) {
        super(series, series.getEpsilon());
    }
}
