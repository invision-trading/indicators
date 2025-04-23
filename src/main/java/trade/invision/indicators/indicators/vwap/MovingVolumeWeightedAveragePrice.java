package trade.invision.indicators.indicators.vwap;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link MovingVolumeWeightedAveragePrice} is a {@link Num} {@link Indicator} to provide a Moving Volume-Weighted
 * Average Price (MVWAP) over a <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.investopedia.com/articles/trading/11/trading-with-vwap-mvwap.asp">Investopedia</a>
 */
public class MovingVolumeWeightedAveragePrice extends Indicator<Num> {

    /**
     * @see #movingVolumeWeightedAveragePrice(BarSeries, int, BiFunction)
     */
    public static MovingVolumeWeightedAveragePrice mvwap(BarSeries barSeries, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return movingVolumeWeightedAveragePrice(barSeries, length, averagingIndicatorSupplier);
    }

    /**
     * Convenience static method for {@link #MovingVolumeWeightedAveragePrice(BarSeries, int, BiFunction)}.
     */
    public static MovingVolumeWeightedAveragePrice movingVolumeWeightedAveragePrice(BarSeries barSeries, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return new MovingVolumeWeightedAveragePrice(barSeries, length, averagingIndicatorSupplier);
    }

    private final Indicator<Num> averagingIndicator;

    /**
     * Instantiates a new {@link MovingVolumeWeightedAveragePrice}.
     *
     * @param barSeries                  the {@link BarSeries}
     * @param length                     the number of values to look back at
     * @param averagingIndicatorSupplier the {@link BiFunction} to supply the averaging {@link Indicator}
     */
    public MovingVolumeWeightedAveragePrice(BarSeries barSeries, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        super(barSeries, length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        averagingIndicator = averagingIndicatorSupplier
                .apply(new VolumeWeightedAveragePrice(barSeries, length), length);
    }

    @Override
    protected Num calculate(long index) {
        return averagingIndicator.getValue(index);
    }
}
