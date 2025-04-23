package trade.invision.indicators.indicators.statistical;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.previous.PreviousRatio;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link GrowthRate} is a {@link Num} {@link Indicator} to provide the statistical growth rate (GR) over a
 * <code>length</code> of values. If the provided <code>length</code> represents 1 year (about 251 trading days), this
 * will provide the Compound Annual Growth Rate (CAGR). The percentage is represented as a fractional. For example, a
 * provided value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/a/annual-return.asp">Investopedia</a>
 */
public class GrowthRate extends Indicator<Num> {

    /**
     * @see #growthRate(Indicator, int)
     */
    public static GrowthRate gr(Indicator<Num> indicator, int length) {
        return growthRate(indicator, length);
    }

    /**
     * Convenience static method for {@link #GrowthRate(Indicator, int)}.
     */
    public static GrowthRate growthRate(Indicator<Num> indicator, int length) {
        return new GrowthRate(indicator, length);
    }

    private final Num exponent;
    private final PreviousRatio previousRatio;

    /**
     * Instantiates a new {@link GrowthRate}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public GrowthRate(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        exponent = numOf(length).reciprocal();
        previousRatio = new PreviousRatio(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        return previousRatio.getValue(index).power(exponent).decrement();
    }
}
