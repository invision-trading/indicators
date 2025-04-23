package trade.invision.indicators.indicators.rsi;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.extrema.local.LocalMaximum;
import trade.invision.indicators.indicators.extrema.local.LocalMinimum;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link StochasticRelativeStrengthIndex} is a {@link Num} {@link Indicator} to provide the Stochastic Relative
 * Strength Index (StochRSI) over a <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/s/stochrsi.asp">Investopedia</a>
 */
public class StochasticRelativeStrengthIndex extends Indicator<Num> {

    /**
     * @see #stochasticRelativeStrengthIndex(Indicator, int, MovingAverageSupplier)
     */
    public static StochasticRelativeStrengthIndex stochrsi(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return stochasticRelativeStrengthIndex(indicator, length, movingAverageSupplier);
    }

    /**
     * Convenience static method for {@link #StochasticRelativeStrengthIndex(Indicator, int, MovingAverageSupplier)}.
     */
    public static StochasticRelativeStrengthIndex stochasticRelativeStrengthIndex(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return new StochasticRelativeStrengthIndex(indicator, length, movingAverageSupplier);
    }

    private final RelativeStrengthIndex rsi;
    private final LocalMinimum minRsi;
    private final LocalMaximum maxRsi;

    /**
     * Instantiates a new {@link StochasticRelativeStrengthIndex}.
     *
     * @param indicator             the {@link Indicator}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public StochasticRelativeStrengthIndex(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        rsi = new RelativeStrengthIndex(indicator, length, movingAverageSupplier);
        minRsi = new LocalMinimum(rsi, length);
        maxRsi = new LocalMaximum(rsi, length);
    }

    @Override
    protected Num calculate(long index) {
        final Num minRsiValue = minRsi.getValue(index);
        final Num maxRsiValue = maxRsi.getValue(index);
        return rsi.getValue(index).subtract(minRsiValue).divide(maxRsiValue.subtract(minRsiValue));
    }
}
