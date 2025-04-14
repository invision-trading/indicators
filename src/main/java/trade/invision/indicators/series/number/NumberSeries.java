package trade.invision.indicators.series.number;

import trade.invision.indicators.series.Series;

import java.util.List;

/**
 * {@link NumberSeries} is a {@link Series} of {@link NumberDatapoint}s.
 */
public class NumberSeries extends Series<NumberDatapoint> {

    /**
     * Instantiates a new {@link NumberSeries}.
     *
     * @param maximumLength the {@link #getMaximumLength()}
     */
    public NumberSeries(int maximumLength) {
        super(maximumLength);
    }

    /**
     * Instantiates a new {@link NumberSeries}.
     *
     * @param initialValues the {@link List} of {@link NumberDatapoint}s
     * @param maximumLength the {@link #getMaximumLength()}
     */
    public NumberSeries(List<NumberDatapoint> initialValues, int maximumLength) {
        super(initialValues, maximumLength);
    }
}
