package trade.invision.indicators.indicator.operation.unary;

import trade.invision.indicators.indicator.Indicator;

import static trade.invision.indicators.indicator.operation.unary.UnaryOperation.unaryOperation;

/**
 * {@link BooleanUnaryOperations} provides convenience static methods for creating {@link Boolean}
 * {@link UnaryOperation} {@link Indicator}s.
 */
public final class BooleanUnaryOperations {

    /**
     * Creates a {@link Boolean} {@link Indicator} that invokes the logical <i>NOT</i> (inverse) unary operation on the
     * given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Boolean> not(Indicator<Boolean> operand) {
        return unaryOperation(o -> !o, operand);
    }
}
