package trade.invision.indicators.indicator.operation.unary;

import trade.invision.indicators.indicator.Indicator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static java.time.ZoneOffset.UTC;
import static trade.invision.indicators.indicator.operation.unary.UnaryOperation.unaryOperation;

/**
 * {@link InstantUnaryOperations} provides convenience static methods for creating {@link Instant}
 * {@link UnaryOperation} {@link Indicator}s.
 */
public final class InstantUnaryOperations {

    /**
     * Calls {@link #zeroTime(Indicator, ZoneId)} with <code>zoneId</code> set to {@link ZoneOffset#UTC}.
     */
    public static Indicator<Instant> zeroTime(Indicator<Instant> operand) {
        return zeroTime(operand, UTC);
    }

    /**
     * Creates an {@link Instant} {@link Indicator} that performs a unary operation to zero-out the time part
     * ({@link LocalTime#MIN}) on the given <code>operand</code>.
     *
     * @param operand the operand
     * @param zoneId  the {@link ZoneId} to perform the operation in
     *
     * @return the {@link Instant} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Instant> zeroTime(Indicator<Instant> operand, ZoneId zoneId) {
        return unaryOperation(o -> o.atZone(zoneId).with(LocalTime.MIN).toInstant(), operand);
    }

    /**
     * @see #epochDate(Indicator)
     */
    public static Indicator<Instant> zeroDate(Indicator<Instant> operand) {
        return epochDate(operand);
    }

    /**
     * Creates an {@link Instant} {@link Indicator} that performs a unary operation to set the date part to
     * {@link LocalDate#EPOCH} on the given <code>operand</code>.
     *
     * @param operand the operand
     *
     * @return the {@link Instant} {@link Indicator}
     *
     * @see UnaryOperation
     */
    public static Indicator<Instant> epochDate(Indicator<Instant> operand) {
        return unaryOperation(o -> o.atZone(UTC).with(LocalDate.EPOCH).toInstant(), operand);
    }
}
