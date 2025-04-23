package trade.invision.indicators.indicators.tr;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link AverageTrueRange} is a {@link Num} {@link Indicator} to provide an Average True Range (ATR) over a
 * <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.investopedia.com/terms/a/atr.asp">Investopedia</a>
 */
public class AverageTrueRange extends Indicator<Num> {

    /**
     * @see #averageTrueRange(BarSeries, int, MovingAverageSupplier)
     */
    public static AverageTrueRange atr(BarSeries barSeries, int length, MovingAverageSupplier movingAverageSupplier) {
        return averageTrueRange(barSeries, length, movingAverageSupplier);
    }

    /**
     * Convenience static method for {@link #AverageTrueRange(BarSeries, int, MovingAverageSupplier)}.
     */
    public static AverageTrueRange averageTrueRange(BarSeries barSeries, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return new AverageTrueRange(barSeries, length, movingAverageSupplier);
    }

    private final Indicator<Num> movingAverage;

    /**
     * Instantiates a new {@link AverageTrueRange}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public AverageTrueRange(BarSeries barSeries, int length, MovingAverageSupplier movingAverageSupplier) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        movingAverage = movingAverageSupplier.supply(new TrueRange(barSeries), length);
    }

    @Override
    protected Num calculate(long index) {
        return movingAverage.getValue(index);
    }
}
