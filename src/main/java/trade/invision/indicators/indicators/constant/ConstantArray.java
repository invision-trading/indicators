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
     * Calls {@link #constantArray(Series, Collection, boolean)} with {@link Series#listView()} on <code>series</code>.
     */
    public static <T> ConstantArray<T> constantArray(Series<T> series, boolean loop) {
        return constantArray(series, series.listView(), loop);
    }

    /**
     * Calls {@link #constantArray(Series, Object[], boolean)} with {@link Collection#toArray()} on
     * <code>constantArray</code>.
     */
    @SuppressWarnings("unchecked")
    public static <T> ConstantArray<T> constantArray(Series<?> series, Collection<T> constantArray, boolean loop) {
        return constantArray(series, (T[]) constantArray.toArray(), loop);
    }

    /**
     * Calls {@link #constantArray(Series, Object[], boolean)} with varargs <code>constantArray</code>.
     */
    @SafeVarargs
    public static <T> ConstantArray<T> constantArray(Series<?> series, boolean loop, T... constantArray) {
        return new ConstantArray<>(series, constantArray, loop);
    }

    /**
     * Gets a {@link ConstantArray}.
     *
     * @param series        the {@link #getSeries()}
     * @param constantArray the constant array
     * @param loop          <code>true</code> to loop back to the beginning of <code>constantArray</code> when the end
     *                      is reached, otherwise the last value of <code>constantArray</code> will be used when the end
     *                      is reached
     */
    public static <T> ConstantArray<T> constantArray(Series<?> series, T[] constantArray, boolean loop) {
        return new ConstantArray<>(series, constantArray, loop);
    }

    private final T[] constantArray;
    private final boolean loop;

    protected ConstantArray(Series<?> series, T[] constantArray, boolean loop) {
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
