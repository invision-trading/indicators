package trade.invision.indicators.indicators.mad;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link MovingAverageDistancePercentage} is a {@link Num} {@link Indicator} to provide the Moving Average Distance
 * Percentage (MADP) over a <code>length</code> of values. The percentage is represented as a fractional. For example, a
 * provided value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a
 * href="https://chartschool.stockcharts.com/table-of-contents/technical-indicators-and-overlays/technical-indicators/distance-from-moving-average">StockCharts</a>
 */
public class MovingAverageDistancePercentage extends Indicator<Num> {

    /**
     * @see #movingAverageDistancePercentage(Indicator, int, BiFunction)
     */
    public static MovingAverageDistancePercentage madp(Indicator<Num> indicator, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return movingAverageDistancePercentage(indicator, length, averagingIndicatorSupplier);
    }

    /**
     * Convenience static method for {@link #MovingAverageDistancePercentage(Indicator, int, BiFunction)}.
     */
    public static MovingAverageDistancePercentage movingAverageDistancePercentage(Indicator<Num> indicator, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return new MovingAverageDistancePercentage(indicator, length, averagingIndicatorSupplier);
    }

    private final Indicator<Num> indicator;
    private final Indicator<Num> ma;

    /**
     * Instantiates a new {@link MovingAverageDistancePercentage}.
     *
     * @param indicator                  the {@link Indicator}
     * @param length                     the number of values to look back at
     * @param averagingIndicatorSupplier the {@link BiFunction} to supply the averaging {@link Indicator}
     */
    public MovingAverageDistancePercentage(Indicator<Num> indicator, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        ma = averagingIndicatorSupplier.apply(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).divide(ma.getValue(index)).decrement();
    }
}
