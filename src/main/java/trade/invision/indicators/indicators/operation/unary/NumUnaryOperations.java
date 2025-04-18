package trade.invision.indicators.indicators.operation.unary;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import java.math.MathContext;
import java.math.RoundingMode;

/**
 * {@link NumUnaryOperations} provides convenience static methods for creating {@link Num} {@link UnaryOperation}
 * {@link Indicator}s.
 */
public final class NumUnaryOperations {

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#square()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> square(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::square, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#cube()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> cube(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::cube, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#exponential()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> exponential(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::exponential, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#squareRoot()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> squareRoot(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::squareRoot, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#cubeRoot()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> cubeRoot(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::cubeRoot, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#naturalLogarithm()} unary operation on the
     * given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> naturalLogarithm(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::naturalLogarithm, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#commonLogarithm()} unary operation on the
     * given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> commonLogarithm(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::commonLogarithm, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#binaryLogarithm()} unary operation on the
     * given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> binaryLogarithm(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::binaryLogarithm, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#absoluteValue()} unary operation on the
     * given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> absoluteValue(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::absoluteValue, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#negate()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> negate(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::negate, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#reciprocal()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> reciprocal(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::reciprocal, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#increment()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> increment(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::increment, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#decrement()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> decrement(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::decrement, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#floor()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> floor(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::floor, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#ceil()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> ceil(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::ceil, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#degrees()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> degrees(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::degrees, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#radians()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> radians(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::radians, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#pi()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> pi(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::pi, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#e()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> e(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::e, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#sine()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> sine(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::sine, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#cosine()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> cosine(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::cosine, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#tangent()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> tangent(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::tangent, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#inverseSine()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> inverseSine(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::inverseSine, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#inverseCosine()} unary operation on the
     * given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> inverseCosine(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::inverseCosine, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#inverseTangent()} unary operation on the
     * given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> inverseTangent(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::inverseTangent, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#hyperbolicSine()} unary operation on the
     * given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> hyperbolicSine(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::hyperbolicSine, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#hyperbolicCosine()} unary operation on the
     * given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> hyperbolicCosine(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::hyperbolicCosine, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#hyperbolicTangent()} unary operation on the
     * given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> hyperbolicTangent(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::hyperbolicTangent, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#inverseHyperbolicSine()} unary operation on
     * the given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> inverseHyperbolicSine(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::inverseHyperbolicSine, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#inverseHyperbolicCosine()} unary operation on
     * the given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> inverseHyperbolicCosine(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::inverseHyperbolicCosine, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#inverseHyperbolicTangent()} unary operation
     * on the given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> inverseHyperbolicTangent(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::inverseHyperbolicTangent, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#integerPart()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> integerPart(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::integerPart, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#fractionalPart()} unary operation on the
     * given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> fractionalPart(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::fractionalPart, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#round()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> round(Indicator<Num> operand) {
        return new UnaryOperation<>(Num::round, operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#round(int)} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     * @param scale   the scale
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> round(Indicator<Num> operand, int scale) {
        return new UnaryOperation<>(o -> o.round(scale), operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#round(RoundingMode)} unary operation on the
     * given <code>operand</code>.
     *
     * @param operand      the operand
     * @param roundingMode the {@link RoundingMode}
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> round(Indicator<Num> operand, RoundingMode roundingMode) {
        return new UnaryOperation<>(o -> o.round(roundingMode), operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#round(int, RoundingMode)} unary operation on
     * the given <code>operand</code>.
     *
     * @param operand      the operand
     * @param scale        the scale
     * @param roundingMode the {@link RoundingMode}
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> round(Indicator<Num> operand, int scale, RoundingMode roundingMode) {
        return new UnaryOperation<>(o -> o.round(scale, roundingMode), operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#precision(int)} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand            the operand
     * @param significantFigures the significant figures
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> precision(Indicator<Num> operand, int significantFigures) {
        return new UnaryOperation<>(o -> o.precision(significantFigures), operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#precision(int, RoundingMode)} unary operation
     * on the given <code>operand</code>.
     *
     * @param operand            the operand
     * @param significantFigures the significant figures
     * @param roundingMode       the {@link RoundingMode}
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> precision(Indicator<Num> operand, int significantFigures, RoundingMode roundingMode) {
        return new UnaryOperation<>(o -> o.precision(significantFigures, roundingMode), operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#precision(MathContext)} unary operation on
     * the given <code>operand</code>.
     *
     * @param operand     the operand
     * @param mathContext the {@link MathContext}
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> precision(Indicator<Num> operand, MathContext mathContext) {
        return new UnaryOperation<>(o -> o.precision(mathContext), operand);
    }

    /**
     * Creates a {@link Num} {@link Indicator} that invokes the {@link Num#signum()} unary operation on the given
     * <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Num} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Num> signum(Indicator<Num> operand) {
        return new UnaryOperation<>(o -> operand.getSeries().getNumFactory().of(o.signum()), operand);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isNegative()} unary comparison using the
     * given <code>input</code>.
     *
     * @param input the input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Boolean> isNegative(Indicator<Num> input) {
        return new UnaryOperation<>(Num::isNegative, input);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isNegativeOrZero(Num)} unary comparison
     * using the given <code>input</code>.
     *
     * @param input the input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Boolean> isNegativeOrZero(Indicator<Num> input) {
        return new UnaryOperation<>(o -> o.isNegativeOrZero(input.getSeries().getEpsilon()), input);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isPositive()} unary comparison using the
     * given <code>input</code>.
     *
     * @param input the input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Boolean> isPositive(Indicator<Num> input) {
        return new UnaryOperation<>(Num::isPositive, input);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isPositiveOrZero(Num)} unary comparison
     * using the given <code>input</code>.
     *
     * @param input the input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Boolean> isPositiveOrZero(Indicator<Num> input) {
        return new UnaryOperation<>(o -> o.isPositiveOrZero(input.getSeries().getEpsilon()), input);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isZero(Num)} unary comparison using the
     * given <code>input</code>.
     *
     * @param input the input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Boolean> isZero(Indicator<Num> input) {
        return new UnaryOperation<>(o -> o.isZero(input.getSeries().getEpsilon()), input);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Num#isNaN()} unary comparison using the
     * given
     * <code>input</code>.
     *
     * @param input the input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Boolean> isNaN(Indicator<Num> input) {
        return new UnaryOperation<>(Num::isNaN, input);
    }
}
