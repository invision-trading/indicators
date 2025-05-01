package trade.invision.indicators.indicators.operation.ternary;

import trade.invision.indicators.indicators.Indicator;

import java.time.Instant;

import static trade.invision.indicators.indicators.operation.ternary.TernaryOperation.ternaryOperation;

/**
 * {@link InstantTernaryOperations} provides convenience static methods for creating {@link Instant}
 * {@link TernaryOperation} {@link Indicator}s.
 */
public final class InstantTernaryOperations {

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs a ternary comparison to check if <code>instant</code>
     * is inclusively between the <code>minimum</code> and <code>maximum</code> inputs.
     *
     * @param instant the input
     * @param minimum the minimum input (inclusive)
     * @param maximum the maximum input (inclusive)
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see TernaryOperation
     */
    public static Indicator<Boolean> isBetween(Indicator<Instant> instant,
            Indicator<Instant> minimum, Indicator<Instant> maximum) {
        return ternaryOperation((i, min, max) -> !i.isBefore(min) && !i.isAfter(max), instant, minimum, maximum);
    }

    /**
     * Creates an {@link Instant} {@link Indicator} that performs an if-else ternary operation on the given operands.
     *
     * @param conditional    the conditional
     * @param trueCondition  the true condition
     * @param falseCondition the false condition
     *
     * @return the {@link Instant} {@link Indicator}
     *
     * @see TernaryOperation
     */
    public static Indicator<Instant> ifElse(Indicator<Boolean> conditional,
            Indicator<Instant> trueCondition, Indicator<Instant> falseCondition) {
        return ternaryOperation((c, t, f) -> c ? t : f, conditional, trueCondition, falseCondition);
    }
}
