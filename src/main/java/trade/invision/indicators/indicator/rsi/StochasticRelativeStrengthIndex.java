package trade.invision.indicators.indicator.rsi;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.extrema.local.LocalMaximum;
import trade.invision.indicators.indicator.extrema.local.LocalMinimum;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicator.extrema.local.LocalMaximum.localMaximum;
import static trade.invision.indicators.indicator.extrema.local.LocalMinimum.localMinimum;
import static trade.invision.indicators.indicator.rsi.RelativeStrengthIndex.relativeStrengthIndex;

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
     * Gets a {@link StochasticRelativeStrengthIndex}.
     *
     * @param indicator             the {@link Indicator}
     * @param length                the number of values to look back at
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static StochasticRelativeStrengthIndex stochasticRelativeStrengthIndex(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(indicator, length, movingAverageSupplier),
                key -> new StochasticRelativeStrengthIndex(indicator, length, movingAverageSupplier));
    }

    private static final Cache<CacheKey, StochasticRelativeStrengthIndex> CACHE =
            Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final RelativeStrengthIndex rsi;
    private final LocalMinimum minRsi;
    private final LocalMaximum maxRsi;

    protected StochasticRelativeStrengthIndex(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        rsi = relativeStrengthIndex(indicator, length, movingAverageSupplier);
        minRsi = localMinimum(rsi, length);
        maxRsi = localMaximum(rsi, length);
    }

    @Override
    protected Num calculate(long index) {
        final Num minRsiValue = minRsi.getValue(index);
        final Num maxRsiValue = maxRsi.getValue(index);
        return rsi.getValue(index).subtract(minRsiValue).divide(maxRsiValue.subtract(minRsiValue));
    }
}
