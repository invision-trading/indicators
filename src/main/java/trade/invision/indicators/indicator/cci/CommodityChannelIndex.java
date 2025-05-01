package trade.invision.indicators.indicator.cci;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Value;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.ma.MovingAverageSupplier;
import trade.invision.indicators.indicator.statistical.MeanDeviation;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicator.statistical.MeanDeviation.meanDeviation;

/**
 * {@link CommodityChannelIndex} is a {@link Num} {@link Indicator} to provide the Commodity Channel Index (CCI) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/c/commoditychannelindex.asp">Investopedia</a>
 */
public class CommodityChannelIndex extends Indicator<Num> {

    /**
     * @see #commodityChannelIndex(Indicator, int, MovingAverageSupplier)
     */
    public static CommodityChannelIndex cci(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return commodityChannelIndex(indicator, length, movingAverageSupplier);
    }

    /**
     * Gets a {@link CommodityChannelIndex}.
     *
     * @param indicator             the {@link Indicator}
     * @param length                the number of values to look back at (typically 20)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public static CommodityChannelIndex commodityChannelIndex(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return CACHE.get(new CacheKey(indicator, length, movingAverageSupplier),
                key -> new CommodityChannelIndex(indicator, length, movingAverageSupplier));
    }

    private static final Cache<CacheKey, CommodityChannelIndex> CACHE = Caffeine.newBuilder().weakValues().build();

    @Value
    private static class CacheKey {

        Indicator<Num> indicator;
        int length;
        MovingAverageSupplier movingAverageSupplier;
    }

    private final Indicator<Num> indicator;
    private final Indicator<Num> averagingIndicator;
    private final MeanDeviation meanDeviation;
    private final Num fifteenThousands;

    protected CommodityChannelIndex(Indicator<Num> indicator, int length, MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        averagingIndicator = movingAverageSupplier.supply(indicator, length);
        meanDeviation = meanDeviation(indicator, length);
        fifteenThousands = numOf("0.015");
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).subtract(averagingIndicator.getValue(index))
                .divide(fifteenThousands.multiply(meanDeviation.getValue(index)));
    }
}
