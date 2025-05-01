package trade.invision.indicators.indicator;

import trade.invision.indicators.series.Series;

import static java.lang.Math.max;

/**
 * {@link RecursiveIndicator} is an abstract {@link CachingIndicator} class for {@link Indicator} subclasses that use
 * recursion in the {@link #calculate(long)} method implementation. If {@link #getValue(long)} is called with an empty
 * {@link Indicator} cache, a {@link StackOverflowError} may occur, so this class replaces recursion with iteration.
 *
 * @param <T> the {@link Indicator} type
 */
public abstract class RecursiveIndicator<T> extends CachingIndicator<T> {

    /**
     * @see CachingIndicator#CachingIndicator(Series, int)
     */
    public RecursiveIndicator(Series<?> series, int minimumStableIndex) {
        super(series, minimumStableIndex);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public T getValue(long index) {
        for (long seriesIndex = max(series.getStartIndex() - 1, cacheSeries.getEndIndex() + 1);
                seriesIndex < index; seriesIndex++) {
            super.getValue(seriesIndex);
        }
        return super.getValue(index);
    }
}
