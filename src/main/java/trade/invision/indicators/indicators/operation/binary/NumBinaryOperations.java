package trade.invision.indicators.indicators.operation.binary;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

/**
 * {@link NumBinaryOperations} provides convenience static methods for creating {@link Num} {@link BinaryOperation}
 * {@link Indicator}s.
 */
public final class NumBinaryOperations {

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#add(Num)} binary operation on the given
     * operands.
     *
     * @param augend the augend operand
     * @param addend the addend operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> add(Indicator<Num> augend, Indicator<Num> addend) {
        return new BinaryOperation<>(Num::add, augend, addend);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#subtract(Num)} binary operation on the given
     * operands.
     *
     * @param minuend    the minuend operand
     * @param subtrahend the subtrahend operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> subtract(Indicator<Num> minuend, Indicator<Num> subtrahend) {
        return new BinaryOperation<>(Num::subtract, minuend, subtrahend);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#multiply(Num)} binary operation on the given
     * operands.
     *
     * @param multiplicand the multiplicand operand
     * @param multiplier   the multiplier operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> multiply(Indicator<Num> multiplicand, Indicator<Num> multiplier) {
        return new BinaryOperation<>(Num::multiply, multiplicand, multiplier);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#divide(Num)} binary operation on the given
     * operands.
     *
     * @param dividend the dividend operand
     * @param divisor  the divisor operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> divide(Indicator<Num> dividend, Indicator<Num> divisor) {
        return new BinaryOperation<>(Num::divide, dividend, divisor);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#remainder(Num)} binary operation on the given
     * operands.
     *
     * @param dividend the dividend operand
     * @param divisor  the divisor operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> remainder(Indicator<Num> dividend, Indicator<Num> divisor) {
        return new BinaryOperation<>(Num::remainder, dividend, divisor);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#power(Num)} binary operation on the given
     * operands.
     *
     * @param base     the base operand
     * @param exponent the exponent operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> power(Indicator<Num> base, Indicator<Num> exponent) {
        return new BinaryOperation<>(Num::power, base, exponent);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#nthRoot(Num)} binary operation on the given
     * operands.
     *
     * @param radicand the radicand operand
     * @param degree   the degree operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> nthRoot(Indicator<Num> radicand, Indicator<Num> degree) {
        return new BinaryOperation<>(Num::nthRoot, radicand, degree);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#logarithm(Num)} binary operation on the given
     * operands.
     *
     * @param antiLogarithm the anti-logarithm operand
     * @param base          the base operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> logarithm(Indicator<Num> antiLogarithm, Indicator<Num> base) {
        return new BinaryOperation<>(Num::logarithm, antiLogarithm, base);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#inverseTangent2(Num)} binary operation on the
     * given operands.
     *
     * @param y the y operand
     * @param x the x operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> inverseTangent2(Indicator<Num> y, Indicator<Num> x) {
        return new BinaryOperation<>(Num::inverseTangent2, y, x);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#hypotenuse(Num)} binary operation on the
     * given operands.
     *
     * @param x the x operand
     * @param y the y operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> hypotenuse(Indicator<Num> x, Indicator<Num> y) {
        return new BinaryOperation<>(Num::hypotenuse, x, y);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#average(Num)} binary operation on the given
     * operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> average(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation<>(Num::average, left, right);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#minimum(Num)} binary operation on the given
     * operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> minimum(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation<>(Num::minimum, left, right);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#maximum(Num)} binary operation on the given
     * operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> maximum(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation<>(Num::maximum, left, right);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#ifNaN(Num)} binary operation on the given
     * operands.
     *
     * @param num         the operand
     * @param replacement the replacement operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> ifNaN(Indicator<Num> num, Indicator<Num> replacement) {
        return new BinaryOperation<>(Num::ifNaN, num, replacement);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isEqual(Num, Num)} binary comparison
     * using the given inputs.
     *
     * @param left  the left input
     * @param right the right input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isEqual(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation<>((l, r) -> l.isEqual(r, left.getSeries().getEpsilon()), left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isLessThan(Num)} binary comparison using
     * the given inputs.
     *
     * @param left  the left input
     * @param right the right input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isLessThan(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation<>(Num::isLessThan, left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isLessThanOrEqual(Num, Num)} binary
     * comparison using the given inputs.
     *
     * @param left  the left input
     * @param right the right input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isLessThanOrEqual(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation<>((l, r) -> l.isLessThanOrEqual(r, left.getSeries().getEpsilon()), left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isGreaterThan(Num)} binary comparison
     * using the given inputs.
     *
     * @param left  the left input
     * @param right the right input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isGreaterThan(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation<>(Num::isGreaterThan, left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isGreaterThanOrEqual(Num, Num)} binary
     * comparison using the given inputs.
     *
     * @param left  the left input
     * @param right the right input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isGreaterThanOrEqual(Indicator<Num> left, Indicator<Num> right) {
        return new BinaryOperation<>((l, r) -> l.isGreaterThanOrEqual(r, left.getSeries().getEpsilon()), left, right);
    }
}
