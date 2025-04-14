package trade.invision.indicators.series.bar;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;

import java.util.List;

/**
 * {@link BarSeries} is a {@link Series} of {@link Bar}s. {@link Bar}s that are added to this {@link BarSeries} that do
 * not have identical {@link Bar#getTimespan()}s or are not added chronologically may cause {@link Indicator}s to behave
 * unexpectedly.
 */
public class BarSeries extends Series<Bar> {

    /**
     * Instantiates a new {@link BarSeries}.
     *
     * @param maximumLength the {@link #getMaximumLength()}
     */
    public BarSeries(int maximumLength) {
        super(maximumLength);
    }

    /**
     * Instantiates a new {@link BarSeries}.
     *
     * @param initialValues the {@link List} of {@link Bar}s
     * @param maximumLength the {@link #getMaximumLength()}
     */
    public BarSeries(List<Bar> initialValues, int maximumLength) {
        super(initialValues, maximumLength);
    }
}
