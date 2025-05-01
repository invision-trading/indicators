package trade.invision.indicators.indicators.bb;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bb.BollingerBandsResult.BollingerBandsResultBuilder;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.indicators.indicators.statistical.StandardDeviation;
import trade.invision.num.Num;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.bb.BollingerBandsResultType.BANDWIDTH;
import static trade.invision.indicators.indicators.bb.BollingerBandsResultType.LOWER_BAND;
import static trade.invision.indicators.indicators.bb.BollingerBandsResultType.MIDDLE_BAND;
import static trade.invision.indicators.indicators.bb.BollingerBandsResultType.PERCENT_B;
import static trade.invision.indicators.indicators.bb.BollingerBandsResultType.UPPER_BAND;
import static trade.invision.indicators.indicators.statistical.StandardDeviation.standardDeviation;

/**
 * {@link BollingerBands} is a {@link Num} {@link Indicator} to provide the Bollinger Bands (BB) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/b/bollingerbands.asp">Investopedia</a>
 */
public class BollingerBands extends Indicator<BollingerBandsResult> {

    /**
     * @see #bollingerBands(Indicator, Set, int, Num, MovingAverageSupplier, boolean)
     */
    public static BollingerBands bb(Indicator<Num> indicator,
            Set<BollingerBandsResultType> resultTypes, int length, Num multiplier,
            MovingAverageSupplier movingAverageSupplier, boolean unbiased) {
        return bollingerBands(indicator, resultTypes, length, multiplier, movingAverageSupplier, unbiased);
    }

    /**
     * Gets a {@link BollingerBands}.
     *
     * @param indicator             the {@link Indicator}
     * @param resultTypes           the {@link BollingerBandsResultType} {@link Set}.
     *                              {@link BollingerBandsResultType#MIDDLE_BAND} is always included.
     * @param length                the number of values to look back at
     * @param multiplier            the multiplier (typically 2)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     * @param unbiased              <code>true</code> to use <code>n - 1</code> (unbiased) for the divisor in the
     *                              standard deviation calculation, <code>false</code> to use <code>n</code> (biased)
     */
    public static BollingerBands bollingerBands(Indicator<Num> indicator,
            Set<BollingerBandsResultType> resultTypes, int length, Num multiplier,
            MovingAverageSupplier movingAverageSupplier, boolean unbiased) {
        final Set<BollingerBandsResultType> finalResultTypes = new HashSet<>(resultTypes);
        finalResultTypes.add(MIDDLE_BAND);
        return CACHE.get(new CacheKey(indicator, finalResultTypes, length, multiplier, movingAverageSupplier, unbiased),
                key -> new BollingerBands(indicator, finalResultTypes, length, multiplier,
                        movingAverageSupplier, unbiased));
    }

    private static final Cache<CacheKey, BollingerBands> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        Set<BollingerBandsResultType> resultTypes;
        int length;
        Num multiplier;
        MovingAverageSupplier movingAverageSupplier;
        boolean unbiased;
    }

    private final Indicator<Num> indicator;
    private final Set<BollingerBandsResultType> resultTypes;
    private final Num multiplier;
    private final StandardDeviation standardDeviation;
    private final Indicator<Num> averagingIndicator;

    protected BollingerBands(Indicator<Num> indicator, Set<BollingerBandsResultType> resultTypes, int length,
            Num multiplier, MovingAverageSupplier movingAverageSupplier, boolean unbiased) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        checkArgument(resultTypes != null && !resultTypes.isEmpty(), "'resultTypes' must not be null or empty!");
        this.indicator = indicator;
        this.resultTypes = resultTypes;
        this.multiplier = multiplier;
        standardDeviation = standardDeviation(indicator, length, unbiased);
        averagingIndicator = movingAverageSupplier.supply(indicator, length);
    }

    @Override
    protected BollingerBandsResult calculate(long index) {
        final BollingerBandsResultBuilder result = BollingerBandsResult.builder();
        final Num middleBand = averagingIndicator.getValue(index);
        result.middleBand(middleBand);
        if (resultTypes.contains(UPPER_BAND) || resultTypes.contains(LOWER_BAND) ||
                resultTypes.contains(BANDWIDTH) || resultTypes.contains(PERCENT_B)) {
            final Num stdDev = standardDeviation.getValue(index);
            final Num stdDevMultiplied = stdDev.multiply(multiplier);
            final Num upperBand = middleBand.add(stdDevMultiplied);
            result.upperBand(upperBand);
            final Num lowerBand = middleBand.subtract(stdDevMultiplied);
            result.lowerBand(lowerBand);
            if (resultTypes.contains(BANDWIDTH)) {
                result.bandwidth(upperBand.subtract(lowerBand).divide(middleBand));
            }
            if (resultTypes.contains(PERCENT_B)) {
                result.percentB(indicator.getValue(index).subtract(lowerBand).divide(upperBand.subtract(lowerBand)));
            }
        }
        return result.build();
    }
}
