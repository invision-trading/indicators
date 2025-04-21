package trade.invision.indicators.indicators.cumulative;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link CumulativeProduct} is a {@link Num} {@link Indicator} to provide the cumulative product (multiplication) over
 * a <code>length</code> of values. This is also known as a running product. In mathematical notation, this is denoted
 * by a capital pi.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Multiplication#Capital_pi_notation">Wikipedia</a>
 */
public class CumulativeProduct extends Indicator<Num> {

    /**
     * @see #cumulativeProduct(Indicator, int)
     */
    public static CumulativeProduct multiplication(Indicator<Num> indicator, int length) {
        return cumulativeProduct(indicator, length);
    }

    /**
     * Convenience static method for {@link #CumulativeProduct(Indicator, int)}.
     */
    public static CumulativeProduct cumulativeProduct(Indicator<Num> indicator, int length) {
        return new CumulativeProduct(indicator, length);
    }

    private final Indicator<Num> indicator;
    private final int length;

    /**
     * Instantiates a new {@link CumulativeProduct}.
     *
     * @param indicator the {@link Num} {@link Indicator}
     * @param length    the number of values to multiply over
     */
    public CumulativeProduct(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator.caching();
        this.length = length;
    }

    @Override
    protected Num calculate(long index) {
        Num product = numOfOne();
        for (long productIndex = max(0, index - length + 1); productIndex <= index; productIndex++) {
            product = product.multiply(indicator.getValue(productIndex));
        }
        return product;
    }
}
