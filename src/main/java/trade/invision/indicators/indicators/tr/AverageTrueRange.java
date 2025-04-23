package trade.invision.indicators.indicators.tr;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link AverageTrueRange} is a {@link Num} {@link Indicator} to provide an Average True Range (ATR) over a
 * <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.investopedia.com/terms/a/atr.asp">Investopedia</a>
 */
public class AverageTrueRange extends Indicator<Num> {

    /**
     * @see #averageTrueRange(BarSeries, int, BiFunction)
     */
    public static AverageTrueRange atr(BarSeries barSeries, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return averageTrueRange(barSeries, length, averagingIndicatorSupplier);
    }

    /**
     * Convenience static method for {@link #AverageTrueRange(BarSeries, int, BiFunction)}.
     */
    public static AverageTrueRange averageTrueRange(BarSeries barSeries, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return new AverageTrueRange(barSeries, length, averagingIndicatorSupplier);
    }

    private final Indicator<Num> averagingIndicator;

    /**
     * Instantiates a new {@link AverageTrueRange}.
     *
     * @param barSeries                  the {@link BarSeries}
     * @param length                     the number of values to look back at
     * @param averagingIndicatorSupplier the {@link BiFunction} to supply the averaging {@link Indicator}
     */
    public AverageTrueRange(BarSeries barSeries, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        averagingIndicator = averagingIndicatorSupplier.apply(new TrueRange(barSeries), length);
    }

    @Override
    protected Num calculate(long index) {
        return averagingIndicator.getValue(index);
    }
}
