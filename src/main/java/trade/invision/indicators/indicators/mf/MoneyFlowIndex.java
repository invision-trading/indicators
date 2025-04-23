package trade.invision.indicators.indicators.mf;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.cumulative.CumulativeSum;
import trade.invision.indicators.indicators.mf.directional.DirectionalMoneyFlow;
import trade.invision.indicators.indicators.mf.directional.DirectionalMoneyFlowResult;
import trade.invision.indicators.indicators.operation.unary.UnaryOperation;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link MoneyFlowIndex} is a {@link Num} {@link Indicator} to provide the Money Flow Index (MFI) over a
 * <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.investopedia.com/terms/m/mfi.asp">Investopedia</a>
 */
public class MoneyFlowIndex extends Indicator<Num> {

    /**
     * @see #moneyFlowIndex(BarSeries, int)
     */
    public static MoneyFlowIndex mf(BarSeries barSeries, int length) {
        return moneyFlowIndex(barSeries, length);
    }

    /**
     * Convenience static method for {@link #MoneyFlowIndex(BarSeries, int)}.
     */
    public static MoneyFlowIndex moneyFlowIndex(BarSeries barSeries, int length) {
        return new MoneyFlowIndex(barSeries, length);
    }

    private final CumulativeSum positiveMoneyFlow;
    private final CumulativeSum negativeMoneyFlow;

    /**
     * Instantiates a new {@link MoneyFlowIndex}.
     *
     * @param barSeries the {@link BarSeries}
     * @param length    the number of values to look back at
     */
    public MoneyFlowIndex(BarSeries barSeries, int length) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        final DirectionalMoneyFlow directionalMoneyFlow = new DirectionalMoneyFlow(barSeries);
        positiveMoneyFlow = new CumulativeSum(new UnaryOperation<>(DirectionalMoneyFlowResult::getPositive,
                directionalMoneyFlow), length);
        negativeMoneyFlow = new CumulativeSum(new UnaryOperation<>(DirectionalMoneyFlowResult::getNegative,
                directionalMoneyFlow), length);
    }

    @Override
    protected Num calculate(long index) {
        final Num positiveMoneyFlowSum = positiveMoneyFlow.getValue(index);
        final Num negativeMoneyFlowSum = negativeMoneyFlow.getValue(index);
        return numOfHundred().subtract(numOfHundred().divide(numOfOne()
                .add(positiveMoneyFlowSum.divide(negativeMoneyFlowSum)))).ifNaN(numOfHundred());
    }
}
