package trade.invision.indicators.series;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;

/**
 * {@link Series} is an abstract class that provides a simple interface for storing and retrieving values from a series.
 * Values can only be added to this {@link Series}, except for the last value, which can be replaced with a new value.
 * When the number of values stored in this {@link Series} exceed a configurable maximum upon adding a new value, then
 * values at the beginning of this {@link Series} are removed. Stored values should be of an immutable type. This class
 * is not thread-safe.
 *
 * @param <T> the immutable type
 */
public abstract class Series<T> {

    /**
     * The name of this {@link Series}.
     */
    protected final @Getter @Nullable String name;
    /**
     * The maximum number of values that this {@link Series} will store internally in memory. If the number of values
     * stored in this {@link Series} exceed this limit upon adding a new value, then the values at the beginning of this
     * {@link Series} are removed.
     */
    protected final @Getter int maximumLength;
    protected final List<T> values;
    /**
     * The index of the first value in this {@link Series}, which changes due to {@link #getMaximumLength()}.
     */
    protected @Getter int startIndex;
    /**
     * The index of the last value in this {@link Series}, which changes due to {@link #getMaximumLength()}.
     */
    protected @Getter int endIndex;
    /**
     * A status flag used to track if this {@link Series} has been modified since creation or since the last call to
     * {@link #resetModifiedStatus()}.
     */
    protected @Getter boolean modified;

    /**
     * Instantiates a new {@link Series}.
     *
     * @param name          the name
     * @param maximumLength the {@link #getMaximumLength()}
     */
    public Series(@Nullable String name, int maximumLength) {
        this(name, new ArrayList<>(0), maximumLength);
    }

    /**
     * Instantiates a new {@link Series}.
     *
     * @param name          the name
     * @param initialValues the {@link List} of values
     * @param maximumLength the {@link #getMaximumLength()}
     */
    public Series(@Nullable String name, List<T> initialValues, int maximumLength) {
        checkArgument(maximumLength > 0, "'maximumLength' must be greater than zero!");
        this.name = name;
        this.maximumLength = maximumLength;
        values = new ArrayList<>(initialValues); // 'ArrayList' has the fastest sequential access time
        if (initialValues.isEmpty()) {
            startIndex = -1;
            endIndex = -1;
        } else {
            if (values.size() > maximumLength) {
                values.subList(0, values.size() - maximumLength).clear();
            }
            startIndex = 0;
            endIndex = initialValues.size() - 1;
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
        modified = true;
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
    public T get(int index) {
        try {
            return values.get(max(index - startIndex, 0));
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new IndexOutOfBoundsException(format("length=%d, startIndex=%d, endIndex=%d, index=%d",
                    getLength(), startIndex, endIndex, index));
        }
    }

    /**
     * Calls {@link #get(int)} with {@link #getStartIndex()}.
     *
     * @return the first value in this {@link Series}
     */
    public T getFirst() {
        return get(startIndex);
    }

    /**
     * Calls {@link #get(int)} with {@link #getEndIndex()}.
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
    public int getLength() {
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

    /**
     * Resets {@link #isModified()}.
     */
    public void resetModifiedStatus() {
        modified = false;
    }
}
