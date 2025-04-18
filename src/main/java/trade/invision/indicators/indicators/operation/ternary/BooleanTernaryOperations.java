package trade.invision.indicators.indicators.operation.ternary;

import trade.invision.indicators.indicators.Indicator;

/**
 * {@link BooleanTernaryOperations} provides convenience static methods for creating {@link Boolean}
 * {@link TernaryOperation} {@link Indicator}s.
 */
public final class BooleanTernaryOperations {

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs an if-else ternary operation on the given operands.
     *
     * @param conditional    the conditional
     * @param trueCondition  the true condition
     * @param falseCondition the false condition
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see TernaryOperation
     */
    public static Indicator<Boolean> ifElse(Indicator<Boolean> conditional,
            Indicator<Boolean> trueCondition, Indicator<Boolean> falseCondition) {
        return new TernaryOperation<>((c, t, f) -> c ? t : f, conditional, trueCondition, falseCondition);
    }
}
