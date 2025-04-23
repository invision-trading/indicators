package trade.invision.indicators.indicators.supertrend;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.RecursiveIndicator;
import trade.invision.indicators.indicators.bar.Close;
import trade.invision.indicators.indicators.barprice.Hl2;
import trade.invision.indicators.indicators.previous.PreviousValue;
import trade.invision.indicators.indicators.tr.AverageTrueRange;
import trade.invision.num.Num;

/**
 * {@link SupertrendUpperBand} is a {@link Num} {@link Indicator} to provide the upper band of a Supertrend over a
 * <code>length</code> of values.
 *
 * @see <a href="https://www.tradingview.com/support/solutions/43000634738-supertrend/">TradingView</a>
 */
public class SupertrendUpperBand extends RecursiveIndicator<Num> {

    /**
     * Convenience static method for {@link #SupertrendUpperBand(Num, AverageTrueRange, Hl2, PreviousValue)}.
     */
    public static SupertrendUpperBand supertrendUpperBand(Num multiplier, AverageTrueRange atr, Hl2 hl2,
            PreviousValue<Num> previousClose) {
        return new SupertrendUpperBand(multiplier, atr, hl2, previousClose);
    }

    private final Num multiplier;
    private final AverageTrueRange atr;
    private final Hl2 hl2;
    private final PreviousValue<Num> previousClose;

    /**
     * Instantiates a new {@link SupertrendUpperBand}.
     *
     * @param multiplier    the multiplier
     * @param atr           the {@link AverageTrueRange}
     * @param hl2           the {@link Hl2}
     * @param previousClose the {@link PreviousValue} of {@link Close}
     */
    public SupertrendUpperBand(Num multiplier, AverageTrueRange atr, Hl2 hl2, PreviousValue<Num> previousClose) {
        super(atr.getSeries(), atr.getMinimumStableIndex());
        this.multiplier = multiplier;
        this.atr = atr;
        this.hl2 = hl2;
        this.previousClose = previousClose;
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return numOfZero();
        }
        final Num currentBasic = hl2.getValue(index).add(multiplier.multiply(atr.getValue(index)));
        final Num previousValue = getValue(index - 1);
        final Num previousClosePrice = previousClose.getValue(index);
        return currentBasic.isLessThan(previousValue) || previousClosePrice.isGreaterThan(previousValue) ?
                currentBasic : previousValue;
    }
}
