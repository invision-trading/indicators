package trade.invision.indicators.indicators;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;
import trade.invision.num.NumFactory;

import java.math.BigDecimal;

/**
 * {@link Indicator} is an abstract class for performing calculations on a {@link Series} or another {@link Indicator},
 * with optional caching. Calculated values should be of an immutable type. This class is not thread-safe.
 *
 * @param <T> the immutable type
 *
 * @see <a href="https://www.investopedia.com/terms/i/indicator.asp">Investopedia</a>
 */
@ToString @EqualsAndHashCode
public abstract class Indicator<T> {

    /**
     * The {@link Series} this {@link Indicator} may use in calculations.
     */
    protected final @Getter Series<?> series;
    /**
     * The number of input datapoints this {@link Indicator} requires in order to perform calculations correctly.
     */
    protected final @Getter int unstableCount;
    protected @Nullable CacheSeries cacheSeries;
    protected long cachedIndex;
    protected @Nullable T cachedValue;
    protected long cachedAddCallCount;

    /**
     * Instantiates a new {@link Indicator}.
     *
     * @param series        the {@link #getSeries()}
     * @param unstableCount the {@link #getUnstableCount()}
     * @param cache         the <code>boolean</code> passed to {@link #cache(boolean)}
     */
    public Indicator(Series<?> series, int unstableCount, boolean cache) {
        this.series = series;
        this.unstableCount = unstableCount;
        cache(cache);
        cachedIndex = -1;
        cachedAddCallCount = -1;
    }

    /**
     * Performs the calculation of this {@link Indicator} at the given <code>index</code>.
     *
     * @param index the index
     *
     * @return the result (should never be <code>null</code>)
     */
    protected abstract T calculate(long index);

    /**
     * Computes the value of this {@link Indicator} at the given <code>index</code>. Regardless of {@link #isCaching()},
     * if {@link #getSeries()} has not been modified since the previous method call was invoked the same
     * <code>index</code> as the current method call, then this {@link Indicator} does not recalculate the value for
     * <code>index</code> and a cached value is returned. If {@link #isCaching()} is <code>true</code>, then the cached
     * value for <code>index</code> is returned if it has been previously calculated, except if <code>index</code> is
     * equal to {@link #getSeries()} {@link Series#getEndIndex()} and {@link #getSeries()} has changed, then the value
     * is recalculated and the cache is updated. If <code>index</code> is greater than {@link #getSeries()}
     * {@link Series#getEndIndex()}, then the calculation is attempted, but may throw an
     * {@link IndexOutOfBoundsException}. If <code>index</code> is less than {@link #getSeries()}
     * {@link Series#getStartIndex()}, then <code>0</code> is used to avoid calculating on a value that has already been
     * removed from {@link #getSeries()} and to use this {@link Indicator}'s default first value so that a
     * {@link StackOverflowError} is avoided in recursive calculations.
     *
     * @param index the index
     *
     * @return the result (never <code>null</code>)
     *
     * @see #cache(boolean)
     */
    public T getValue(long index) {
        if (index > series.getEndIndex()) {
            return calculate(index);
        } else if (index < series.getStartIndex()) {
            return calculate(0);
        } else if (index == cachedIndex && series.getAddCallCount() == cachedAddCallCount) {
            return cachedValue;
        } else {
            T value;
            if (cacheSeries != null) {
                // Ensure the 'cacheSeries' indices follow the 'series' indices.
                for (long count = cacheSeries.getEndIndex(); count < series.getEndIndex(); count++) {
                    cacheSeries.add(null);
                }
                if (index == series.getEndIndex() && series.getAddCallCount() != cachedAddCallCount) {
                    value = calculate(index);
                    cacheSeries.replaceLast(value);
                } else {
                    value = cacheSeries.get(index);
                    if (value == null) {
                        value = calculate(index);
                        cacheSeries.set(index, value);
                    }
                }
            } else {
                value = calculate(index);
            }
            cachedIndex = index;
            cachedValue = value;
            cachedAddCallCount = series.getAddCallCount();
            return value;
        }
    }

    protected final class CacheSeries extends Series<T> {

        private CacheSeries() {
            super(series.getMaximumLength());
        }

        private void set(long index, T value) {
            values.set((int) (index - startIndex), value);
        }
    }

    /**
     * Sets whether this {@link Indicator} will cache its calculated values in order to optimize
     * {@link #getValue(long)}. The cache size is equal to {@link #getSeries()} {@link Series#getMaximumLength()}.
     *
     * @param cache <code>true</code> to enable caching, <code>false</code> otherwise
     */
    public void cache(boolean cache) {
        if (cache) {
            if (cacheSeries == null) {
                cacheSeries = new CacheSeries();
            }
        } else {
            cacheSeries = null;
        }
    }

    /**
     * Convenience method to call {@link #cache(boolean)} with <code>true</code>.
     *
     * @return this {@link Indicator}, for method chaining
     *
     * @see #cache(boolean)
     */
    @SuppressWarnings("unchecked")
    public <A extends Indicator<T>> A caching() {
        cache(true);
        return (A) this;
    }

    /**
     * Convenience method to call {@link #cache(boolean)} with <code>false</code>.
     *
     * @return this {@link Indicator}, for method chaining
     *
     * @see #cache(boolean)
     */
    @SuppressWarnings("unchecked")
    public <A extends Indicator<T>> A noCache() {
        cache(false);
        return (A) this;
    }

    /**
     * @return <code>true</code> if this {@link Indicator} has caching enabled, <code>false</code> otherwise
     *
     * @see #cache(boolean)
     */
    public boolean isCaching() {
        return cacheSeries != null;
    }

    /**
     * Checks if the {@link #getSeries()} of this {@link Indicator} contains enough datapoints for the calculations of
     * this {@link Indicator} to be accurate. The following logic is used to determine if this {@link Indicator} is
     * stable: <code>isStable = {@link Series#getLength()} > {@link #getUnstableCount()}</code>
     *
     * @return <code>true</code> if this {@link Indicator}'s calculations are stable and accurate, <code>false</code> if
     * calculations are unstable and inaccurate
     */
    public boolean isStable() {
        return series.getLength() > unstableCount;
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#of(Number)}.
     */
    public Num numOf(Number number) {
        return series.getNumFactory().of(number);
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#of(BigDecimal)}.
     */
    public Num numOf(BigDecimal bigDecimal) {
        return series.getNumFactory().of(bigDecimal);
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#of(String)}.
     */
    public Num numOf(String string) {
        return series.getNumFactory().of(string);
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#of(Num)}.
     */
    public Num numOf(Num num) {
        return series.getNumFactory().of(num);
    }

    /**
     * Convenience method for {@link #constant(Object)} with {@link #numOf(Number)}.
     */
    public Indicator<Num> constant(Number number) {
        return constant(numOf(number));
    }

    /**
     * Convenience method for {@link #constant(Object)} with {@link #numOf(BigDecimal)}.
     */
    public Indicator<Num> constant(BigDecimal bigDecimal) {
        return constant(numOf(bigDecimal));
    }

    /**
     * Convenience method for {@link #constant(Object)} with {@link #numOf(String)}.
     */
    public Indicator<Num> constant(String string) {
        return constant(numOf(string));
    }

    /**
     * Convenience method to provide a new {@link Indicator} that always yields the given <code>constant</code>.
     *
     * @param <C>      the type of {@link Indicator}
     * @param constant the constant
     *
     * @return the constant {@link Indicator}
     */
    public <C> Indicator<C> constant(C constant) {
        return new Indicator<>(series, 0, false) {

            @Override
            protected C calculate(long index) {
                return constant;
            }
        };
    }
}
