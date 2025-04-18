package trade.invision.indicators.indicators.operation.binary;

import trade.invision.indicators.indicators.Indicator;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static java.time.ZoneOffset.UTC;

/**
 * {@link InstantBinaryOperations} provides convenience static methods for creating {@link Instant}
 * {@link BinaryOperation} {@link Indicator}s.
 */
public final class InstantBinaryOperations {

    /**
     * Creates an {@link Instant} {@link Indicator} that invokes the {@link Instant#isBefore(Instant)} binary operation
     * on the given operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Instant} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Instant> earliest(Indicator<Instant> left, Indicator<Instant> right) {
        return new BinaryOperation<>((l, r) -> l.isBefore(r) ? l : r, left, right);
    }

    /**
     * Creates an {@link Instant} {@link Indicator} that invokes the {@link Instant#isAfter(Instant)} binary operation
     * on the given operands.
     *
     * @param left  the left operand
     * @param right the right operand
     *
     * @return the {@link Instant} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Instant> latest(Indicator<Instant> left, Indicator<Instant> right) {
        return new BinaryOperation<>((l, r) -> l.isAfter(r) ? l : r, left, right);
    }

    /**
     * Calls {@link #withTimeOf(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to {@link ZoneOffset#UTC}.
     */
    public static Indicator<Instant> withTimeOf(Indicator<Instant> left, Indicator<Instant> right) {
        return withTimeOf(left, right, UTC);
    }

    /**
     * Creates an {@link Instant} {@link Indicator} that performs a binary operation to set the time part of the
     * <code>left</code> operand to the time part of the <code>right</code> operand.
     *
     * @param left   the left operand
     * @param right  the right operand
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Instant} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Instant> withTimeOf(Indicator<Instant> left, Indicator<Instant> right, ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                l.atZone(zoneId).with(r.atZone(zoneId).toLocalTime()).toInstant(), left, right);
    }

    /**
     * Calls {@link #withDateOf(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to {@link ZoneOffset#UTC}.
     */
    public static Indicator<Instant> withDateOf(Indicator<Instant> left, Indicator<Instant> right) {
        return withDateOf(left, right, UTC);
    }

    /**
     * Creates an {@link Instant} {@link Indicator} that performs a binary operation to set the date part of the
     * <code>left</code> operand to the date part of the <code>right</code> operand.
     *
     * @param left   the left operand
     * @param right  the right operand
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Instant} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Instant> withDateOf(Indicator<Instant> left, Indicator<Instant> right, ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                l.atZone(zoneId).with(r.atZone(zoneId).toLocalDate()).toInstant(), left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Instant#equals(Object)} binary comparison
     * using the given inputs.
     *
     * @param left  the left input
     * @param right the right input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isEqual(Indicator<Instant> left, Indicator<Instant> right) {
        return new BinaryOperation<>(Instant::equals, left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Instant#isBefore(Instant)} binary comparison
     * using the given inputs.
     *
     * @param left  the left input
     * @param right the right input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isBefore(Indicator<Instant> left, Indicator<Instant> right) {
        return new BinaryOperation<>(Instant::isBefore, left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the inverse {@link Instant#isAfter(Instant)} binary
     * comparison using the given inputs.
     *
     * @param left  the left input
     * @param right the right input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isBeforeOrEqual(Indicator<Instant> left, Indicator<Instant> right) {
        return new BinaryOperation<>((l, r) -> !l.isAfter(r), left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link Instant#isAfter(Instant)} binary comparison
     * using the given inputs.
     *
     * @param left  the left input
     * @param right the right input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isAfter(Indicator<Instant> left, Indicator<Instant> right) {
        return new BinaryOperation<>(Instant::isAfter, left, right);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the inverse {@link Instant#isBefore(Instant)} binary
     * comparison using the given inputs.
     *
     * @param left  the left input
     * @param right the right input
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isAfterOrEqual(Indicator<Instant> left, Indicator<Instant> right) {
        return new BinaryOperation<>((l, r) -> !l.isBefore(r), left, right);
    }

    // TODO add 'isDateEqual', 'isTimeEqual', and so on
}
