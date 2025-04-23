package trade.invision.indicators.indicators.ui;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link UlcerIndex} is a {@link Num} {@link Indicator} to provide the Ulcer Index (UI) over a <code>length</code> of
 * values.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Ulcer_index">Wikipedia</a>
 */
public class UlcerIndex extends Indicator<Num> {

    /**
     * @see #ulcerIndex(Indicator, int)
     */
    public static UlcerIndex ui(Indicator<Num> indicator, int length) {
        return ulcerIndex(indicator, length);
    }

    /**
     * Convenience static method for {@link #UlcerIndex(Indicator, int)}.
     */
    public static UlcerIndex ulcerIndex(Indicator<Num> indicator, int length) {
        return new UlcerIndex(indicator, length);
    }

    private final Indicator<Num> indicator;
    private final int length;

    /**
     * Instantiates a new {@link UlcerIndex}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public UlcerIndex(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        this.length = length;
    }

    @Override
    protected Num calculate(long index) {
        final long startIndex = max(0, index - length + 1);
        final long observations = index - startIndex + 1;
        Num highestValue = null;
        Num meanSquare = numOfZero();
        for (long indicatorIndex = startIndex + 1; indicatorIndex <= index; indicatorIndex++) {
            final Num value = indicator.getValue(indicatorIndex);
            if (highestValue == null || value.isGreaterThan(highestValue)) {
                highestValue = value;
            }
            meanSquare = meanSquare.add(value.divide(highestValue).decrement().multiply(numOfHundred())).square();
        }
        return meanSquare.divide(observations).squareRoot();
    }
}
