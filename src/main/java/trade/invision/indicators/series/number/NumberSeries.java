package trade.invision.indicators.series.number;

import org.jetbrains.annotations.Nullable;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;
import trade.invision.num.NumFactory;

import java.util.List;

/**
 * {@link NumberSeries} is a {@link Series} of {@link NumberDatapoint}s.
 */
public class NumberSeries extends Series<NumberDatapoint> {

    /**
     * @see Series#Series(int)
     */
    public NumberSeries(int maximumLength) {
        super(maximumLength);
    }

    /**
     * @see Series#Series(List, int)
     */
    public NumberSeries(List<NumberDatapoint> initialValues, int maximumLength) {
        super(initialValues, maximumLength);
    }

    /**
     * @see Series#Series(List, int, NumFactory, Num)
     */
    public NumberSeries(List<NumberDatapoint> initialValues, int maximumLength,
            @Nullable NumFactory numFactory, @Nullable Num epsilon) {
        super(initialValues, maximumLength, numFactory, epsilon);
    }
}
