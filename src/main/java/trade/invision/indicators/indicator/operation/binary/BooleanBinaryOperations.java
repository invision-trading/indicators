package trade.invision.indicators.indicator.operation.binary;

import trade.invision.indicators.indicator.Indicator;

import static trade.invision.indicators.indicator.operation.binary.BinaryOperation.binaryOperation;

/**
 * {@link BooleanBinaryOperations} provides convenience static methods for creating {@link Boolean}
 * {@link BinaryOperation} {@link Indicator}s.
 */
public final class BooleanBinaryOperations {

    /**
     * Creates a {@link Boolean} {@link Indicator} that invokes the logical <i>AND</i> binary operation on the given
     * operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> and(Indicator<Boolean> left, Indicator<Boolean> right) {
        return binaryOperation((l, r) -> l & r, left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that invokes the logical <i>OR</i> binary operation on the given
     * operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> or(Indicator<Boolean> left, Indicator<Boolean> right) {
        return binaryOperation((l, r) -> l | r, left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that invokes the logical <i>XOR</i> (exclusive OR) binary operation
     * on the given operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> xor(Indicator<Boolean> left, Indicator<Boolean> right) {
        return binaryOperation((l, r) -> l ^ r, left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs a boolean equality binary comparison using the given
     * inputs.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isEqual(Indicator<Boolean> left, Indicator<Boolean> right) {
        return binaryOperation((l, r) -> l == r, left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs a boolean inequality binary comparison using the given
     * inputs.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isNotEqual(Indicator<Boolean> left, Indicator<Boolean> right) {
        return binaryOperation((l, r) -> l != r, left, right);
    }
}
