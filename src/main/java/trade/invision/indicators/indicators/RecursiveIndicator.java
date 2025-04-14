package trade.invision.indicators.indicators;

import org.jetbrains.annotations.Nullable;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;
import trade.invision.num.NumFactory;

/**
 * {@link RecursiveIndicator} is for {@link Indicator} subclasses that use recursion in the {@link #calculate(long)}
 * method implementation. If {@link #getValue(long)} is called with an empty {@link Indicator} cache, a
 * {@link StackOverflowError} may be thrown, so this class replaces recursion with iteration. Caching is enabled by
 * default and cannot be turned off.
 *
 * @param <T> the {@link Indicator} type
 */
public abstract class RecursiveIndicator<T> extends Indicator<T> {

    /**
     * Calls {@link Indicator(Series, NumFactory, int, Num, boolean)} with <code>cache</code> set to <code>true</code>.
     */
    public RecursiveIndicator(Series<?> series, NumFactory numFactory, int unstableCount, @Nullable Num epsilon) {
        super(series, numFactory, unstableCount, epsilon, true);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public T getValue(long index) {
        for (long seriesIndex = cacheSeries.getEndIndex() + 1; seriesIndex < index; seriesIndex++) {
            super.getValue(seriesIndex);
        }
        return super.getValue(index);
    }

    @Override
    public void cache(boolean cache) {
        super.cache(true);
    }
}
