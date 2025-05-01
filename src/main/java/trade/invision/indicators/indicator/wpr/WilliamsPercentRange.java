package trade.invision.indicators.indicator.wpr;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.extrema.local.LocalMaximum;
import trade.invision.indicators.indicator.extrema.local.LocalMinimum;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicator.extrema.local.LocalMaximum.localMaximum;
import static trade.invision.indicators.indicator.extrema.local.LocalMinimum.localMinimum;

/**
 * {@link WilliamsPercentRange} is a {@link Num} {@link Indicator} to provide the Williams Percent Range (WPR) over a
 * <code>length</code> of values. Also known as the Williams %R. The percentage is represented as a
 * fractional. For example, a provided value of <code>0.15</code> would represent <code>15%</code>.
 *
 * @see <a href="https://www.investopedia.com/terms/w/williamsr.asp">Investopedia</a>
 */
public class WilliamsPercentRange extends Indicator<Num> {

    /**
     * @see #williamsPercentRange(Indicator, int)
     */
    public static WilliamsPercentRange wpr(Indicator<Num> indicator, int length) {
        return williamsPercentRange(indicator, length);
    }

    /**
     * Gets a {@link WilliamsPercentRange}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at (typically 14)
     */
    public static WilliamsPercentRange williamsPercentRange(Indicator<Num> indicator, int length) {
        return CACHE.get(new CacheKey(indicator, length), key -> new WilliamsPercentRange(indicator, length));
    }

    private static final Cache<CacheKey, WilliamsPercentRange> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
    }

    private final Indicator<Num> indicator;
    private final LocalMaximum highestHigh;
    private final LocalMinimum lowestLow;
    private final Num negativeHundred;

    protected WilliamsPercentRange(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        highestHigh = localMaximum(indicator, length);
        lowestLow = localMinimum(indicator, length);
        negativeHundred = numOf(-100);
    }

    @Override
    protected Num calculate(long index) {
        final Num value = indicator.getValue(index);
        final Num highestHighValue = highestHigh.getValue(index);
        final Num lowestLowValue = lowestLow.getValue(index);
        return highestHighValue.subtract(value).divide(highestHighValue.subtract(lowestLowValue))
                .multiply(negativeHundred);
    }
}
