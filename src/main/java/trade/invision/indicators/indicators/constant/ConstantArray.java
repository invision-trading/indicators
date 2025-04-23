package trade.invision.indicators.indicators.constant;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.Series;

import java.util.Collection;

/**
 * {@link ConstantArray} is an {@link Indicator} to provide a constant array of values.
 */
public class ConstantArray<T> extends CachelessIndicator<T> {

    /**
     * Convenience static method for {@link #ConstantArray(Series, boolean)}.
     */
    public static <T> ConstantArray<T> constantArray(Series<T> series, boolean loop) {
        return new ConstantArray<>(series, loop);
    }

    /**
     * Convenience static method for {@link #ConstantArray(Series, Collection, boolean)}.
     */
    public static <T> ConstantArray<T> constantArray(Series<?> series, Collection<T> constantArray, boolean loop) {
        return new ConstantArray<>(series, constantArray, loop);
    }

    /**
     * Convenience static method for {@link #ConstantArray(Series, Object[], boolean)}.
     */
    @SafeVarargs
    public static <T> ConstantArray<T> constantArray(Series<?> series, boolean loop, T... constantArray) {
        return new ConstantArray<>(series, constantArray, loop);
    }

    /**
     * Convenience static method for {@link #ConstantArray(Series, Object[], boolean)}.
     */
    public static <T> ConstantArray<T> constantArray(Series<?> series, T[] constantArray, boolean loop) {
        return new ConstantArray<>(series, constantArray, loop);
    }

    private final T[] constantArray;
    private final boolean loop;

    /**
     * Calls {@link #ConstantArray(Series, Collection, boolean)} with {@link Series#listView()} on <code>series</code>.
     */
    public ConstantArray(Series<T> series, boolean loop) {
        this(series, series.listView(), loop);
    }

    /**
     * Calls {@link #ConstantArray(Series, Object[], boolean)} with {@link Collection#toArray()} on
     * <code>constantArray</code>.
     */
    @SuppressWarnings("unchecked")
    public ConstantArray(Series<?> series, Collection<T> constantArray, boolean loop) {
        this(series, (T[]) constantArray.toArray(), loop);
    }

    /**
     * Instantiates a new {@link ConstantArray}.
     *
     * @param series        the {@link #getSeries()}
     * @param constantArray the constant array
     * @param loop          <code>true</code> to loop back to the beginning of <code>constantArray</code> when the
     *                      end is reached, otherwise the last value of <code>constantArray</code> will be used when the
     *                      end is reached
     */
    public ConstantArray(Series<?> series, T[] constantArray, boolean loop) {
        super(series, 0);
        this.constantArray = constantArray;
        this.loop = loop;
    }

    @Override
    protected T calculate(long index) {
        if (loop) {
            return constantArray[(int) (index % (long) constantArray.length)];
        } else {
            return index < constantArray.length ? constantArray[(int) index] : constantArray[constantArray.length - 1];
        }
    }
}
