package trade.invision.indicators.indicator.statistical;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.previous.PreviousRatio;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicator.previous.PreviousRatio.previousRatio;

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
     * Gets a {@link GrowthRate}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public static GrowthRate growthRate(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new GrowthRate(indicator, length));
    }

    private static final Cache<CacheKey, GrowthRate> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final Num exponent;
    private final PreviousRatio previousRatio;

    protected GrowthRate(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        exponent = numOf(length).reciprocal();
        previousRatio = previousRatio(indicator, length);
    }

    @Override
    protected Num calculate(long index) {
        return previousRatio.getValue(index).power(exponent).decrement();
    }
}
