package trade.invision.indicators.indicators.mad;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.num.Num;

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
     * @see #movingAverageDistancePercentage(Indicator, int, MovingAverageSupplier)
     */
    public static MovingAverageDistancePercentage madp(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return movingAverageDistancePercentage(indicator, length, movingAverageSupplier);
    }

    /**
     * Convenience static method for {@link #MovingAverageDistancePercentage(Indicator, int, MovingAverageSupplier)}.
     */
    public static MovingAverageDistancePercentage movingAverageDistancePercentage(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return new MovingAverageDistancePercentage(indicator, length, movingAverageSupplier);
    }

    private final Indicator<Num> indicator;
    private final Indicator<Num> ma;

    /**
     * Instantiates a new {@link MovingAverageDistancePercentage}.
     *
     * @param indicator             the {@link Indicator}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public MovingAverageDistancePercentage(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        ma = movingAverageSupplier.supply(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).divide(ma.getValue(index)).decrement();
    }
}
