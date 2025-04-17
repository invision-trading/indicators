package trade.invision.indicators.series.num;

import org.jetbrains.annotations.Nullable;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;
import trade.invision.num.NumFactory;

import java.util.Collection;

/**
 * {@link NumSeries} is a {@link Series} of {@link NumDatapoint}s.
 */
public class NumSeries extends Series<NumDatapoint> {

    /**
     * @see Series#Series(int)
     */
    public NumSeries(int maximumLength) {
        super(maximumLength);
    }

    /**
     * @see Series#Series(Collection, int)
     */
    public NumSeries(Collection<NumDatapoint> initialValues, int maximumLength) {
        super(initialValues, maximumLength);
    }

    /**
     * @see Series#Series(Collection, int, NumFactory, Num)
     */
    public NumSeries(Collection<NumDatapoint> initialValues, int maximumLength,
            @Nullable NumFactory numFactory, @Nullable Num epsilon) {
        super(initialValues, maximumLength, numFactory, epsilon);
    }
}
