package trade.invision.indicators.series;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import trade.invision.num.DoubleNum;
import trade.invision.num.Num;
import trade.invision.num.NumFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static trade.invision.num.DoubleNum.doubleNumFactory;

/**
 * {@link Series} is a class that provides a simple interface for storing and retrieving values from a series. Values
 * can only be added to this {@link Series}, except for the last value, which can be replaced with a new value. When the
 * number of values stored in this {@link Series} exceed a configurable maximum upon adding a new value, then values at
 * the beginning of this {@link Series} are removed. Values should be of an immutable type. This class is not
 * thread-safe.
 *
 * @param <T> the immutable type
 */
public class Series<T> {

    /**
     * The maximum number of values that this {@link Series} will store internally in memory. If the number of values
     * stored in this {@link Series} exceed this limit upon adding a new value, then the values at the beginning of this
     * {@link Series} are removed.
     */
    protected final @Getter int maximumLength;
    /**
     * The {@link NumFactory} that consumers of this {@link Series} may use for creating {@link Num}s.
     */
    protected final @Getter NumFactory numFactory;
    /**
     * The {@link Num} to use as the epsilon in tolerant comparison operations that consumers of this {@link Series} may
     * use.
     */
    protected final @Getter Num epsilon;
    protected final List<T> values;
    /**
     * The index of the first value in this {@link Series}, which changes due to {@link #getMaximumLength()}. This will
     * be <code>-1</code> if {@link #isEmpty()} is <code>true</code>.
     */
    protected @Getter long startIndex;
    /**
     * The index of the last value in this {@link Series}, which changes due to {@link #getMaximumLength()}. This will
     * be <code>-1</code> if {@link #isEmpty()} is <code>true</code>.
     */
    protected @Getter long endIndex;
    /**
     * Gets the number of times {@link #add(Object, boolean)} has been called. Use to track if this {@link Series} may
     * have changed in any way. Number may overflow.
     */
    protected @Getter long addCallCount;

    /**
     * Calls {@link #Series(Collection, int, NumFactory, Num)} with <code>initialValues</code> set to
     * <code>null</code>, <code>numFactory</code> set to <code>null</code>, and <code>epsilon</code> set to
     * <code>null</code>.
     */
    public Series(int maximumLength) {
        this(null, maximumLength, null, null);
    }

    /**
     * Calls {@link #Series(Collection, int, NumFactory, Num)} with <code>numFactory</code> set to <code>null</code> and
     * <code>epsilon</code> set to <code>null</code>.
     */
    public Series(@Nullable Collection<T> initialValues, int maximumLength) {
        this(initialValues, maximumLength, null, null);
    }

    /**
     * Calls {@link #Series(Collection, int, NumFactory, Num)} with <code>initialValues</code> set to <code>null</code>
     * and <code>epsilon</code> set to <code>null</code>.
     */
    public Series(int maximumLength, @Nullable NumFactory numFactory) {
        this(null, maximumLength, numFactory, null);
    }

    /**
     * Instantiates a new {@link Series}.
     *
     * @param initialValues the initial {@link Collection} of values, or <code>null</code> for no initial values
     * @param maximumLength the {@link #getMaximumLength()}
     * @param numFactory    the {@link #getNumFactory()}, or <code>null</code> for {@link DoubleNum#doubleNumFactory()}
     * @param epsilon       the {@link #getEpsilon()}, or <code>null</code> for {@link NumFactory#zero()}
     */
    public Series(@Nullable Collection<T> initialValues, int maximumLength,
            @Nullable NumFactory numFactory, @Nullable Num epsilon) {
        checkArgument(maximumLength > 0, "'maximumLength' must be greater than zero!");
        this.maximumLength = maximumLength;
        this.numFactory = numFactory != null ? numFactory : doubleNumFactory();
        this.epsilon = epsilon != null ? epsilon : this.numFactory.zero();
        // 'ArrayList' has the fastest sequential access time.
        values = initialValues != null ? new ArrayList<>(initialValues) : new ArrayList<>(0);
        if (values.isEmpty()) {
            startIndex = -1;
            endIndex = -1;
        } else {
            if (values.size() > maximumLength) {
                values.subList(0, values.size() - maximumLength).clear();
            }
            startIndex = 0;
            endIndex = values.size() - 1;
        }
    }

    /**
     * Calls {@link #add(Object, boolean)} with <code>replaceLast</code> set to <code>true</code>.
     */
    public void replaceLast(T value) {
        add(value, true);
    }

    /**
     * Calls {@link #add(Object, boolean)} with <code>replaceLast</code> set to <code>false</code>.
     */
    public void add(T value) {
        add(value, false);
    }

    /**
     * Adds the given <code>value</code> to this {@link Series}.
     *
     * @param value       the value
     * @param replaceLast <code>true</code> to replace the last value in this {@link Series}, <code>false</code> to
     *                    add the value to the end of this {@link Series}
     */
    public void add(T value, boolean replaceLast) {
        addCallCount++;
        if (replaceLast && !values.isEmpty()) {
            values.set(values.size() - 1, value);
            return;
        }
        values.add(value);
        if (startIndex == -1) {
            startIndex = 0;
        }
        endIndex++;
        if (values.size() > maximumLength) {
            values.removeFirst();
            startIndex++;
        }
    }

    /**
     * Gets the value at the given <code>index</code>. If the given <code>index</code> is less than
     * {@link #getStartIndex()}, then {@link #getStartIndex()} is used.
     *
     * @param index the index
     *
     * @return the {@link Series} value
     *
     * @throws IndexOutOfBoundsException thrown if the index is outside the range of this {@link Series}
     */
    public T get(long index) {
        try {
            return values.get((int) max(index - startIndex, 0));
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new IndexOutOfBoundsException(format("length=%d, startIndex=%d, endIndex=%d, index=%d",
                    getLength(), startIndex, endIndex, index));
        }
    }

    /**
     * Calls {@link #get(long)} with {@link #getStartIndex()}.
     *
     * @return the first value in this {@link Series}
     */
    public T getFirst() {
        return get(startIndex);
    }

    /**
     * Calls {@link #get(long)} with {@link #getEndIndex()}.
     *
     * @return the last value in this {@link Series}
     */
    public T getLast() {
        return get(endIndex);
    }

    /**
     * Gets the number of values in this {@link Series}, which changes due to {@link #getMaximumLength()}.
     *
     * @return the length of this {@link Series}
     */
    public long getLength() {
        return endIndex >= 0 ? endIndex - startIndex + 1 : 0;
    }

    /**
     * Checks if this {@link Series} is empty ({@link #getLength()} is <code>0</code>).
     *
     * @return <code>true</code> if empty, <code>false</code> otherwise
     */
    public boolean isEmpty() {
        return getLength() == 0;
    }

    /**
     * Returns a read-only view of this {@link Series} as a {@link List}.
     *
     * @return the {@link List} view
     */
    public List<T> listView() {
        return unmodifiableList(values);
    }
}
