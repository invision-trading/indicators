package trade.invision.indicators.indicators;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import trade.invision.indicators.series.Series;
import trade.invision.num.Num;
import trade.invision.num.NumFactory;

import java.math.BigDecimal;

/**
 * {@link Indicator} is an abstract class for performing calculations on a {@link Series} or another {@link Indicator},
 * with optional caching (disabled by default). Calculated values should be of an immutable type. This class is not
 * thread-safe.
 *
 * @param <T> the immutable type
 *
 * @see <a href="https://www.investopedia.com/terms/i/indicator.asp">Investopedia</a>
 */
public abstract class Indicator<T> {

    /**
     * The {@link Series} this {@link Indicator} may use in calculations.
     */
    protected final @Getter Series<?> series;
    /**
     * The minimum <code>index</code> (inclusive) that this {@link Indicator} will start to perform calculations
     * correctly at.
     */
    protected final @Getter int minimumStableIndex;
    protected @Nullable CacheSeries cacheSeries;
    protected long cachedIndex;
    protected @Nullable T cachedValue;
    protected long cachedAddCallCount;

    /**
     * Instantiates a new {@link Indicator}.
     *
     * @param series             the {@link #getSeries()}
     * @param minimumStableIndex the {@link #getMinimumStableIndex()}
     */
    public Indicator(Series<?> series, int minimumStableIndex) {
        this.series = series;
        this.minimumStableIndex = minimumStableIndex;
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
     * if {@link #getSeries()} has not been modified since the previous method call was invoked with the same
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
     * @see #caching()
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
     * Permanently enables caching of this {@link Indicator}'s calculated values in order to optimize
     * {@link #getValue(long)} for non-ending indices. The cache should be enabled when consumers of this
     * {@link Indicator} use the calculated values of non-ending indices, as opposed to only using the calculated value
     * of the end index (e.g. {@link Series#getEndIndex()}). The cache size is equal to {@link #getSeries()}
     * {@link Series#getMaximumLength()}.
     *
     * @return this {@link Indicator}, for method chaining
     *
     * @see #getValue(long)
     */
    @SuppressWarnings("unchecked")
    public <A extends Indicator<T>> A caching() {
        if (cacheSeries == null) {
            cacheSeries = new CacheSeries();
        }
        return (A) this;
    }

    /**
     * If {@link #isCaching()} is <code>true</code>, purges the cache by resetting {@link #cacheSeries}.
     *
     * @see #caching()
     */
    protected void purgeCache() {
        if (cacheSeries != null) {
            cacheSeries = new CacheSeries();
        }
    }

    /**
     * @return <code>true</code> if this {@link Indicator} has caching enabled, <code>false</code> otherwise
     *
     * @see #caching()
     */
    public boolean isCaching() {
        return cacheSeries != null;
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#of(Number)}.
     */
    protected Num numOf(Number number) {
        return series.getNumFactory().of(number);
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#of(BigDecimal)}.
     */
    protected Num numOf(BigDecimal bigDecimal) {
        return series.getNumFactory().of(bigDecimal);
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#of(String)}.
     */
    protected Num numOf(String string) {
        return series.getNumFactory().of(string);
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#of(Num)}.
     */
    protected Num numOf(Num num) {
        return series.getNumFactory().of(num);
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#negativeOne()}.
     */
    protected Num numOfNegativeOne() {
        return series.getNumFactory().negativeOne();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#zero()}.
     */
    protected Num numOfZero() {
        return series.getNumFactory().zero();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#one()}.
     */
    protected Num numOfOne() {
        return series.getNumFactory().one();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#two()}.
     */
    protected Num numOfTwo() {
        return series.getNumFactory().two();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#three()}.
     */
    protected Num numOfThree() {
        return series.getNumFactory().three();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#four()}.
     */
    protected Num numOfFour() {
        return series.getNumFactory().four();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#five()}.
     */
    protected Num numOfFive() {
        return series.getNumFactory().five();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#six()}.
     */
    protected Num numOfSix() {
        return series.getNumFactory().six();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#seven()}.
     */
    protected Num numOfSeven() {
        return series.getNumFactory().seven();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#eight()}.
     */
    protected Num numOfEight() {
        return series.getNumFactory().eight();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#nine()}.
     */
    protected Num numOfNine() {
        return series.getNumFactory().nine();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#ten()}.
     */
    protected Num numOfTen() {
        return series.getNumFactory().ten();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#hundred()}.
     */
    protected Num numOfHundred() {
        return series.getNumFactory().hundred();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#thousand()}.
     */
    protected Num numOfThousand() {
        return series.getNumFactory().thousand();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#tenth()}.
     */
    protected Num numOfTenth() {
        return series.getNumFactory().tenth();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#hundredth()}.
     */
    protected Num numOfHundredth() {
        return series.getNumFactory().hundredth();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#thousandth()}.
     */
    protected Num numOfThousandth() {
        return series.getNumFactory().thousandth();
    }

    /**
     * Convenience method for {@link #getSeries()} {@link Series#getNumFactory()} {@link NumFactory#half()}.
     */
    protected Num numOfHalf() {
        return series.getNumFactory().half();
    }
}
