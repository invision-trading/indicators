package trade.invision.indicators.indicators.cci;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.statistical.MeanDeviation;
import trade.invision.num.Num;

import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link CommodityChannelIndex} is a {@link Num} {@link Indicator} to provide the Commodity Channel Index (CCI) over a
 * <code>length</code> of values.
 *
 * @see <a href="https://www.investopedia.com/terms/c/commoditychannelindex.asp">Investopedia</a>
 */
public class CommodityChannelIndex extends Indicator<Num> {

    /**
     * @see #commodityChannelIndex(Indicator, int, BiFunction)
     */
    public static CommodityChannelIndex cci(Indicator<Num> indicator, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return commodityChannelIndex(indicator, length, averagingIndicatorSupplier);
    }

    /**
     * Convenience static method for {@link #CommodityChannelIndex(Indicator, int, BiFunction)}.
     */
    public static CommodityChannelIndex commodityChannelIndex(Indicator<Num> indicator, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return new CommodityChannelIndex(indicator, length, averagingIndicatorSupplier);
    }

    private final Indicator<Num> indicator;
    private final Indicator<Num> averagingIndicator;
    private final MeanDeviation meanDeviation;
    private final Num fifteenThousands;

    /**
     * Instantiates a new {@link CommodityChannelIndex}.
     *
     * @param indicator                  the {@link Indicator}
     * @param length                     the number of values to look back at (typically 20)
     * @param averagingIndicatorSupplier the {@link BiFunction} to supply the averaging {@link Indicator}
     */
    public CommodityChannelIndex(Indicator<Num> indicator, int length,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        super(indicator.getSeries(), length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.indicator = indicator;
        averagingIndicator = averagingIndicatorSupplier.apply(indicator, length);
        meanDeviation = new MeanDeviation(indicator, length);
        fifteenThousands = numOf("0.015");
    }

    @Override
    protected Num calculate(long index) {
        return indicator.getValue(index).subtract(averagingIndicator.getValue(index))
                .divide(fifteenThousands.multiply(meanDeviation.getValue(index)));
    }
}
