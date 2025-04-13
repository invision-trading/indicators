package trade.invision.indicators.series.number;

import org.jetbrains.annotations.Nullable;
import trade.invision.indicators.series.Series;

import java.util.List;

/**
 * {@link NumberSeries} is a {@link Series} of {@link NumberDatapoint}s.
 */
public class NumberSeries extends Series<NumberDatapoint> {

    /**
     * Instantiates a new {@link NumberSeries}.
     *
     * @param name          the name
     * @param maximumLength the {@link #getMaximumLength()}
     */
    public NumberSeries(@Nullable String name, int maximumLength) {
        super(name, maximumLength);
    }

    /**
     * Instantiates a new {@link NumberSeries}.
     *
     * @param name          the name
     * @param initialValues the {@link List} of {@link NumberDatapoint}s
     * @param maximumLength the {@link #getMaximumLength()}
     */
    public NumberSeries(@Nullable String name, List<NumberDatapoint> initialValues, int maximumLength) {
        super(name, initialValues, maximumLength);
    }
}
