package trade.invision.indicators.series.bar;

import org.jetbrains.annotations.Nullable;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;
import trade.invision.num.NumFactory;

import java.util.List;

/**
 * {@link BarSeries} is a {@link Series} of {@link Bar}s. {@link Bar}s that are added to this {@link BarSeries} that do
 * not have identical {@link Bar#getTimespan()}s or are not added chronologically may cause {@link Indicator}s to behave
 * unexpectedly.
 */
public class BarSeries extends Series<Bar> {

    /**
     * @see Series#Series(int)
     */
    public BarSeries(int maximumLength) {
        super(maximumLength);
    }

    /**
     * @see Series#Series(List, int)
     */
    public BarSeries(List<Bar> initialValues, int maximumLength) {
        super(initialValues, maximumLength);
    }

    /**
     * @see Series#Series(List, int, NumFactory, Num)
     */
    public BarSeries(List<Bar> initialValues, int maximumLength,
            @Nullable NumFactory numFactory, @Nullable Num epsilon) {
        super(initialValues, maximumLength, numFactory, epsilon);
    }
}
