package trade.invision.indicators.indicators.mad;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link MovingAverageDistanceDifference} is a {@link Num} {@link Indicator} to provide the Moving Average Distance
 * Difference (MADD) over a <code>length</code> of values.
 *
 * @see <a
 * href="https://chartschool.stockcharts.com/table-of-contents/technical-indicators-and-overlays/technical-indicators/distance-from-moving-average">StockCharts</a>
 */
public class MovingAverageDistanceDifference extends Indicator<Num> {

    /**
     * @see #movingAverageDistanceDifference(Indicator, int, MovingAverageSupplier)
     */
    public static MovingAverageDistanceDifference madd(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return movingAverageDistanceDifference(indicator, length, movingAverageSupplier);
    }

    /**
     * Convenience static method for {@link #MovingAverageDistanceDifference(Indicator, int, MovingAverageSupplier)}.
     */
    public static MovingAverageDistanceDifference movingAverageDistanceDifference(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return new MovingAverageDistanceDifference(indicator, length, movingAverageSupplier);
    }

    private final Indicator<Num> indicator;
    private final Indicator<Num> ma;

    /**
     * Instantiates a new {@link MovingAverageDistanceDifference}.
     *
     * @param indicator             the {@link Indicator}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public MovingAverageDistanceDifference(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        ma = movingAverageSupplier.supply(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).subtract(ma.getValue(index));
    }
}
