package trade.invision.indicators.indicators.acdc;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.macd.MovingAverageConvergenceDivergence;
import trade.invision.num.Num;

import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link AccelerationDeceleration} is a {@link Num} {@link Indicator} to provide the Acceleration/Deceleration (ACDC)
 * over a <code>firstLength</code> and <code>secondLength</code> of values.
 *
 * @see <a
 * href="https://toslc.thinkorswim.com/center/reference/Tech-Indicators/studies-library/A-B/AccelerationDecelerationOsc">thinkorswim</a>
 */
public class AccelerationDeceleration extends Indicator<Num> {

    /**
     * @see #accelerationDeceleration(Indicator, int, int, BiFunction)
     */
    public static AccelerationDeceleration acdc(Indicator<Num> indicator,
            int firstLength, int secondLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return accelerationDeceleration(indicator, firstLength, secondLength, averagingIndicatorSupplier);
    }

    /**
     * Convenience static method for {@link #AccelerationDeceleration(Indicator, int, int, BiFunction)}.
     */
    public static AccelerationDeceleration accelerationDeceleration(Indicator<Num> indicator,
            int firstLength, int secondLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return new AccelerationDeceleration(indicator, firstLength, secondLength, averagingIndicatorSupplier);
    }

    private final MovingAverageConvergenceDivergence macd;
    private final Indicator<Num> averagingIndicator;

    /**
     * Instantiates a new {@link AccelerationDeceleration}.
     *
     * @param indicator                  the {@link Indicator}
     * @param firstLength                the first averaging length (typically 5)
     * @param secondLength               the second averaging length (typically 34)
     * @param averagingIndicatorSupplier the {@link BiFunction} to supply the averaging {@link Indicator}
     */
    public AccelerationDeceleration(Indicator<Num> indicator, int firstLength, int secondLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        super(indicator.getSeries(), max(firstLength, secondLength));
        checkArgument(firstLength > 0, "'firstLength' must be greater than zero!");
        checkArgument(secondLength > 0, "'secondLength' must be greater than zero!");
        macd = new MovingAverageConvergenceDivergence(indicator, firstLength, secondLength, averagingIndicatorSupplier);
        averagingIndicator = averagingIndicatorSupplier.apply(macd, firstLength);
    }

    @Override
    protected Num calculate(long index) {
        return macd.getValue(index).subtract(averagingIndicator.getValue(index));
    }
}
