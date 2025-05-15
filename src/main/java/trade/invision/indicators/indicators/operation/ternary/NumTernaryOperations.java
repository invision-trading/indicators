package trade.invision.indicators.indicators.operation.ternary;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static trade.invision.indicators.indicators.operation.ternary.TernaryOperation.ternaryOperation;

/**
 * {@link NumTernaryOperations} provides convenience static methods for creating {@link Num} {@link TernaryOperation}
 * {@link Indicator}s.
 */
public final class NumTernaryOperations {

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#clamp(Num, Num)} ternary operation on the
     * given operands.
     *
     * @param num     the operand
     * @param minimum the minimum operand
     * @param maximum the maximum operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see TernaryOperation
     */
    public static Indicator<Num> clamp(Indicator<Num> num, Indicator<Num> minimum, Indicator<Num> maximum) {
        return ternaryOperation(Num::clamp, num, minimum, maximum);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs a ternary comparison to check if <code>num</code> is
     * inclusively between the <code>minimum</code> and <code>maximum</code> inputs.
     *
     * @param num     the input
     * @param minimum the minimum input (inclusive)
     * @param maximum the maximum input (inclusive)
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see TernaryOperation
     */
    public static Indicator<Boolean> isBetween(Indicator<Num> num, Indicator<Num> minimum, Indicator<Num> maximum) {
        return ternaryOperation((i, min, max) -> i.isGreaterThanOrEqual(min, num.getSeries().getEpsilon()) &&
                i.isLessThanOrEqual(max, num.getSeries().getEpsilon()), num, minimum, maximum);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that performs an if-else ternary operation on the given operands.
     *
     * @param conditional    the conditional
     * @param trueCondition  the true condition
     * @param falseCondition the false condition
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see TernaryOperation
     */
    public static Indicator<Num> ifElse(Indicator<Boolean> conditional,
            Indicator<Num> trueCondition, Indicator<Num> falseCondition) {
        return ternaryOperation((c, t, f) -> c ? t : f, conditional, trueCondition, falseCondition);
    }
}
