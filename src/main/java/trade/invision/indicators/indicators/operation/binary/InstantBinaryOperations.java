package trade.invision.indicators.indicators.operation.binary;

import trade.invision.indicators.indicators.Indicator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;

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
     * Calls {@link #withTime(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to {@link ZoneOffset#UTC}.
     */
    public static Indicator<Instant> withTime(Indicator<Instant> left, Indicator<Instant> right) {
        return withTime(left, right, UTC);
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
    public static Indicator<Instant> withTime(Indicator<Instant> left, Indicator<Instant> right, ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                l.atZone(zoneId).with(r.atZone(zoneId).toLocalTime()).toInstant(), left, right);
    }

    /**
     * Calls {@link #withDate(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to {@link ZoneOffset#UTC}.
     */
    public static Indicator<Instant> withDate(Indicator<Instant> left, Indicator<Instant> right) {
        return withDate(left, right, UTC);
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
    public static Indicator<Instant> withDate(Indicator<Instant> left, Indicator<Instant> right, ZoneId zoneId) {
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

    /**
     * Calls {@link #isTimeEqual(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to {@link ZoneOffset#UTC}.
     */
    public static Indicator<Boolean> isTimeEqual(Indicator<Instant> left, Indicator<Instant> right) {
        return isTimeEqual(left, right, UTC);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link LocalTime#equals(Object)} binary comparison
     * on the time part using the given inputs.
     *
     * @param left   the left input
     * @param right  the right input
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isTimeEqual(Indicator<Instant> left, Indicator<Instant> right, ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                l.atZone(zoneId).toLocalTime().equals(r.atZone(zoneId).toLocalTime()), left, right);
    }

    /**
     * Calls {@link #isTimeBefore(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to
     * {@link ZoneOffset#UTC}.
     */
    public static Indicator<Boolean> isTimeBefore(Indicator<Instant> left, Indicator<Instant> right) {
        return isTimeBefore(left, right, UTC);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link LocalTime#isBefore(LocalTime)} binary
     * comparison on the time part using the given inputs.
     *
     * @param left   the left input
     * @param right  the right input
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isTimeBefore(Indicator<Instant> left, Indicator<Instant> right, ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                l.atZone(zoneId).toLocalTime().isBefore(r.atZone(zoneId).toLocalTime()), left, right);
    }

    /**
     * Calls {@link #isTimeBeforeOrEqual(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to
     * {@link ZoneOffset#UTC}.
     */
    public static Indicator<Boolean> isTimeBeforeOrEqual(Indicator<Instant> left, Indicator<Instant> right) {
        return isTimeBeforeOrEqual(left, right, UTC);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the inverse {@link LocalTime#isAfter(LocalTime)} binary
     * comparison on the time part using the given inputs.
     *
     * @param left   the left input
     * @param right  the right input
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isTimeBeforeOrEqual(Indicator<Instant> left, Indicator<Instant> right,
            ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                !l.atZone(zoneId).toLocalTime().isAfter(r.atZone(zoneId).toLocalTime()), left, right);
    }

    /**
     * Calls {@link #isTimeAfter(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to {@link ZoneOffset#UTC}.
     */
    public static Indicator<Boolean> isTimeAfter(Indicator<Instant> left, Indicator<Instant> right) {
        return isTimeAfter(left, right, UTC);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link LocalTime#isAfter(LocalTime)} binary
     * comparison on the time part using the given inputs.
     *
     * @param left   the left input
     * @param right  the right input
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isTimeAfter(Indicator<Instant> left, Indicator<Instant> right, ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                l.atZone(zoneId).toLocalTime().isAfter(r.atZone(zoneId).toLocalTime()), left, right);
    }

    /**
     * Calls {@link #isTimeAfterOrEqual(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to
     * {@link ZoneOffset#UTC}.
     */
    public static Indicator<Boolean> isTimeAfterOrEqual(Indicator<Instant> left, Indicator<Instant> right) {
        return isTimeAfterOrEqual(left, right, UTC);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the inverse {@link LocalTime#isBefore(LocalTime)}
     * binary comparison on the time part using the given inputs.
     *
     * @param left   the left input
     * @param right  the right input
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isTimeAfterOrEqual(Indicator<Instant> left, Indicator<Instant> right,
            ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                !l.atZone(zoneId).toLocalTime().isBefore(r.atZone(zoneId).toLocalTime()), left, right);
    }

    /**
     * Calls {@link #isDateEqual(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to {@link ZoneOffset#UTC}.
     */
    public static Indicator<Boolean> isDateEqual(Indicator<Instant> left, Indicator<Instant> right) {
        return isDateEqual(left, right, UTC);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link LocalDate#equals(Object)} binary comparison
     * on the date part using the given inputs.
     *
     * @param left   the left input
     * @param right  the right input
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isDateEqual(Indicator<Instant> left, Indicator<Instant> right, ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                l.atZone(zoneId).toLocalDate().equals(r.atZone(zoneId).toLocalDate()), left, right);
    }

    /**
     * Calls {@link #isDateBefore(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to
     * {@link ZoneOffset#UTC}.
     */
    public static Indicator<Boolean> isDateBefore(Indicator<Instant> left, Indicator<Instant> right) {
        return isDateBefore(left, right, UTC);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link LocalDate#isBefore(ChronoLocalDate)} binary
     * comparison on the date part using the given inputs.
     *
     * @param left   the left input
     * @param right  the right input
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isDateBefore(Indicator<Instant> left, Indicator<Instant> right, ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                l.atZone(zoneId).toLocalDate().isBefore(r.atZone(zoneId).toLocalDate()), left, right);
    }

    /**
     * Calls {@link #isDateBeforeOrEqual(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to
     * {@link ZoneOffset#UTC}.
     */
    public static Indicator<Boolean> isDateBeforeOrEqual(Indicator<Instant> left, Indicator<Instant> right) {
        return isDateBeforeOrEqual(left, right, UTC);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the inverse {@link LocalDate#isAfter(ChronoLocalDate)}
     * binary comparison on the date part using the given inputs.
     *
     * @param left   the left input
     * @param right  the right input
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isDateBeforeOrEqual(Indicator<Instant> left, Indicator<Instant> right,
            ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                !l.atZone(zoneId).toLocalDate().isAfter(r.atZone(zoneId).toLocalDate()), left, right);
    }

    /**
     * Calls {@link #isDateAfter(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to {@link ZoneOffset#UTC}.
     */
    public static Indicator<Boolean> isDateAfter(Indicator<Instant> left, Indicator<Instant> right) {
        return isDateAfter(left, right, UTC);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the {@link LocalDate#isAfter(ChronoLocalDate)} binary
     * comparison on the date part using the given inputs.
     *
     * @param left   the left input
     * @param right  the right input
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isDateAfter(Indicator<Instant> left, Indicator<Instant> right, ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                l.atZone(zoneId).toLocalDate().isAfter(r.atZone(zoneId).toLocalDate()), left, right);
    }

    /**
     * Calls {@link #isDateAfterOrEqual(Indicator, Indicator, ZoneId)} with <code>zoneId</code> set to
     * {@link ZoneOffset#UTC}.
     */
    public static Indicator<Boolean> isDateAfterOrEqual(Indicator<Instant> left, Indicator<Instant> right) {
        return isDateAfterOrEqual(left, right, UTC);
    }

    /**
     * Creates a {@link Boolean} {@link Indicator} that performs the inverse {@link LocalDate#isBefore(ChronoLocalDate)}
     * binary comparison on the date part using the given inputs.
     *
     * @param left   the left input
     * @param right  the right input
     * @param zoneId the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Boolean} {@link Indicator}
     *
     * @see BinaryOperation
     */
    public static Indicator<Boolean> isDateAfterOrEqual(Indicator<Instant> left, Indicator<Instant> right,
            ZoneId zoneId) {
        return new BinaryOperation<>((l, r) ->
                !l.atZone(zoneId).toLocalDate().isBefore(r.atZone(zoneId).toLocalDate()), left, right);
    }
}
