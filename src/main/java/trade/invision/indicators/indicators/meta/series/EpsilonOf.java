package trade.invision.indicators.indicators.meta.series;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.constant.ConstantValue;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;

/**
 * {@link EpsilonOf} is a {@link Num} {@link Indicator} to provide the {@link Series#getEpsilon()} of the given
 * {@link Series}.
 */
public class EpsilonOf extends ConstantValue<Num> {

    /**
     * Convenience static method for {@link #EpsilonOf(Series)}.
     */
    public static EpsilonOf epsilonOf(Series<?> series) {
        return new EpsilonOf(series);
    }

    /**
     * Instantiates a new {@link EpsilonOf}.
     *
     * @param series the {@link #getSeries()}
     */
    public EpsilonOf(Series<?> series) {
        super(series, series.getEpsilon());
    }
}
