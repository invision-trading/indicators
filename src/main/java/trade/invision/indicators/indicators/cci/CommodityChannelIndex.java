package trade.invision.indicators.indicators.cci;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.MovingAverageSupplier;
import trade.invision.indicators.indicators.statistical.MeanDeviation;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

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
     * Convenience static method for {@link #CommodityChannelIndex(Indicator, int, MovingAverageSupplier)}.
     */
    public static CommodityChannelIndex commodityChannelIndex(Indicator<Num> indicator, int length,
            MovingAverageSupplier movingAverageSupplier) {
        return new CommodityChannelIndex(indicator, length, movingAverageSupplier);
    }

    private final Indicator<Num> indicator;
    private final Indicator<Num> averagingIndicator;
    private final MeanDeviation meanDeviation;
    private final Num fifteenThousands;

    /**
     * Instantiates a new {@link CommodityChannelIndex}.
     *
     * @param indicator             the {@link Indicator}
     * @param length                the number of values to look back at (typically 20)
     * @param movingAverageSupplier the {@link MovingAverageSupplier}
     */
    public CommodityChannelIndex(Indicator<Num> indicator, int length, MovingAverageSupplier movingAverageSupplier) {
        super(indicator.getSeries(), length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        averagingIndicator = movingAverageSupplier.supply(indicator, length);
        meanDeviation = new MeanDeviation(indicator, length);
        fifteenThousands = numOf("0.015");
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).subtract(averagingIndicator.getValue(index))
                .divide(fifteenThousands.multiply(meanDeviation.getValue(index)));
    }
}
