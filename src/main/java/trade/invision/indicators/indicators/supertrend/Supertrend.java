package trade.invision.indicators.indicators.supertrend;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.RecursiveIndicator;
import trade.invision.indicators.indicators.bar.Close;
import trade.invision.indicators.indicators.barprice.Hl2;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.indicators.indicators.previous.PreviousValue;
import trade.invision.indicators.indicators.tr.AverageTrueRange;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link Supertrend} is a {@link Num} {@link Indicator} to provide the Supertrend over a <code>length</code> of
 * {@link Bar}s.
 *
 * @see <a href="https://www.tradingview.com/support/solutions/43000634738-supertrend/">TradingView</a>
 */
public class Supertrend extends RecursiveIndicator<Num> {

    /**
     * Convenience static method for {@link #Supertrend(BarSeries, int, Num, MovingAverageSupplier)}.
     */
    public static Supertrend supertrend(BarSeries barSeries, int length, Num multiplier,
            MovingAverageSupplier movingAverageSupplier) {
        return new Supertrend(barSeries, length, multiplier, movingAverageSupplier);
    }

    private final Close close;
    private final SupertrendLowerBand lowerBand;
    private final SupertrendUpperBand upperBand;

    /**
     * Instantiates a new {@link Supertrend}.
     *
     * @param barSeries             the {@link BarSeries}
     * @param length                the number of values to look back at (typically 10)
     * @param multiplier            the multiplier (typically 3)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public Supertrend(BarSeries barSeries, int length, Num multiplier, MovingAverageSupplier movingAverageSupplier) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        close = new Close(barSeries);
        final AverageTrueRange atr = new AverageTrueRange(barSeries, length, movingAverageSupplier);
        final Hl2 hl2 = new Hl2(barSeries);
        final PreviousValue<Num> previousClose = new PreviousValue<>(close);
        lowerBand = new SupertrendLowerBand(multiplier, atr, hl2, previousClose);
        upperBand = new SupertrendUpperBand(multiplier, atr, hl2, previousClose);
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return numOfZero();
        }
        final Num closePrice = close.getValue(index);
        final Num previousValue = getValue(index - 1);
        final Num lower = lowerBand.getValue(index);
        final Num upper = upperBand.getValue(index);
        if (previousValue.equals(upperBand.getValue(index - 1))) {
            return closePrice.isGreaterThan(upper) ? lower : upper;
        } else if (previousValue.equals(lowerBand.getValue(index - 1))) {
            return closePrice.isLessThan(lower) ? upper : lower;
        } else {
            return previousValue;
        }
    }
}
