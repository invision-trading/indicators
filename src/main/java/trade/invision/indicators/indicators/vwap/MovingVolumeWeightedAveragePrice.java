package trade.invision.indicators.indicators.vwap;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link MovingVolumeWeightedAveragePrice} is a {@link Num} {@link Indicator} to provide a Moving Volume-Weighted
 * Average Price (MVWAP) over a <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.investopedia.com/articles/trading/11/trading-with-vwap-mvwap.asp">Investopedia</a>
 */
public class MovingVolumeWeightedAveragePrice extends Indicator<Num> {

    /**
     * @see #movingVolumeWeightedAveragePrice(BarSeries, int, MovingAverageSupplier)
     */
    public static MovingVolumeWeightedAveragePrice mvwap(BarSeries barSeries, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return movingVolumeWeightedAveragePrice(barSeries, length, movingAverageSupplier);
    }

    /**
     * Convenience static method for {@link #MovingVolumeWeightedAveragePrice(BarSeries, int, MovingAverageSupplier)}.
     */
    public static MovingVolumeWeightedAveragePrice movingVolumeWeightedAveragePrice(BarSeries barSeries, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return new MovingVolumeWeightedAveragePrice(barSeries, length, movingAverageSupplier);
    }

    private final Indicator<Num> averagingIndicator;

    /**
     * Instantiates a new {@link MovingVolumeWeightedAveragePrice}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public MovingVolumeWeightedAveragePrice(BarSeries barSeries, int length,
            MovingAverageSupplier movingAverageSupplier) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        averagingIndicator = movingAverageSupplier.supply(new VolumeWeightedAveragePrice(barSeries, length), length);
    }

    @Override
    protected Num calculate(long index) {
        return averagingIndicator.getValue(index);
    }
}
