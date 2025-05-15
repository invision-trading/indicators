package trade.invision.indicators.indicators.operation.ternary;

import java.util.function.Function;

/**
 * {@link TriFunction} is a {@link Function} for a three-arity (ternary) operation.
 *
 * @param <A> the type of the first argument to the function
 * @param <B> the type of the second argument to the function
 * @param <C> the type of the third argument to the function
 * @param <R> the type of the result of the function
 *
 * @see <a href="https://en.wikipedia.org/wiki/Ternary_operation">Wikipedia</a>
 */
@FunctionalInterface
public interface TriFunction<A, B, C, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param a the first function argument
     * @param b the second function argument
     * @param c the third function argument
     *
     * @return the function result
     */
    R apply(A a, B b, C c);
}
