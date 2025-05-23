package trade.invision.indicators.indicators.operation.binary;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static trade.invision.indicators.indicators.operation.binary.BinaryOperation.binaryOperation;

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
        return binaryOperation(Num::add, augend, addend);
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
        return binaryOperation(Num::subtract, minuend, subtrahend);
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
        return binaryOperation(Num::multiply, multiplicand, multiplier);
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
        return binaryOperation(Num::divide, dividend, divisor);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#mod(Num)} binary operation on the given
     * operands.
     *
     * @param dividend the dividend operand
     * @param divisor  the divisor operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> mod(Indicator<Num> dividend, Indicator<Num> divisor) {
        return binaryOperation(Num::mod, dividend, divisor);
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
        return binaryOperation(Num::remainder, dividend, divisor);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#pow(Num)} binary operation on the given
     * operands.
     *
     * @param base     the base operand
     * @param exponent the exponent operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> pow(Indicator<Num> base, Indicator<Num> exponent) {
        return binaryOperation(Num::pow, base, exponent);
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
        return binaryOperation(Num::power, base, exponent);
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
        return binaryOperation(Num::nthRoot, radicand, degree);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#log(Num)} binary operation on the given
     * operands.
     *
     * @param antiLogarithm the anti-logarithm operand
     * @param base          the base operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> log(Indicator<Num> antiLogarithm, Indicator<Num> base) {
        return binaryOperation(Num::log, antiLogarithm, base);
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
        return binaryOperation(Num::logarithm, antiLogarithm, base);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#atan2(Num)} binary operation on the given
     * operands.
     *
     * @param y the y operand
     * @param x the x operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> atan2(Indicator<Num> y, Indicator<Num> x) {
        return binaryOperation(Num::atan2, y, x);
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
        return binaryOperation(Num::inverseTangent2, y, x);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#hypot(Num)} binary operation on the given
     * operands.
     *
     * @param x the x operand
     * @param y the y operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> hypot(Indicator<Num> x, Indicator<Num> y) {
        return binaryOperation(Num::hypot, x, y);
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
        return binaryOperation(Num::hypotenuse, x, y);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#avg(Num)} binary operation on the given
     * operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> avg(Indicator<Num> left, Indicator<Num> right) {
        return binaryOperation(Num::avg, left, right);
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
        return binaryOperation(Num::average, left, right);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#min(Num)} binary operation on the given
     * operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> min(Indicator<Num> left, Indicator<Num> right) {
        return binaryOperation(Num::min, left, right);
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
        return binaryOperation(Num::minimum, left, right);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#max(Num)} binary operation on the given
     * operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Num> max(Indicator<Num> left, Indicator<Num> right) {
        return binaryOperation(Num::max, left, right);
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
        return binaryOperation(Num::maximum, left, right);
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
        return binaryOperation(Num::ifNaN, num, replacement);
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
        return binaryOperation((l, r) -> l.isEqual(r, left.getSeries().getEpsilon()), left, right);
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
        return binaryOperation(Num::isLessThan, left, right);
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
        return binaryOperation((l, r) -> l.isLessThanOrEqual(r, left.getSeries().getEpsilon()), left, right);
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
        return binaryOperation(Num::isGreaterThan, left, right);
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
        return binaryOperation((l, r) -> l.isGreaterThanOrEqual(r, left.getSeries().getEpsilon()), left, right);
    }
}
