package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.constant.ConstantValue;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link MaximumLengthOf} is a {@link Num} {@link Indicator} to provide the {@link Series#getMaximumLength()} of the
 * given {@link Series}.
 */
public class MaximumLengthOf extends ConstantValue<Num> {

    /**
     * Convenience static method for {@link #MaximumLengthOf(Series)}.
     */
    public static MaximumLengthOf maximumLengthOf(Series<?> series) {
        return new MaximumLengthOf(series);
    }

    /**
     * Instantiates a new {@link MaximumLengthOf}.
     *
     * @param series the {@link #getSeries()}
     */
    public MaximumLengthOf(Series<?> series) {
        super(series, series.getNumFactory().of(series.getMaximumLength()));
    }
}
