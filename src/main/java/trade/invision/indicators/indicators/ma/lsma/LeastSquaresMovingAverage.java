package trade.invision.indicators.indicators.ma.lsma;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.statistical.regression.LinearRegression;
import trade.invision.indicators.indicators.statistical.regression.LinearRegressionResult;
import trade.invision.num.Num;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.statistical.regression.LinearRegressionResultType.Y;

/**
 * {@link LeastSquaresMovingAverage} is a {@link Num} {@link Indicator} to provide a Least Squares Moving Average (LSMA)
 * over a <code>length</code> of values. Also known as the Time Series Moving Average (TSMA).
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=LstSqrMA.htm">FM Labs</a>
 */
public class LeastSquaresMovingAverage extends Indicator<Num> {

    /**
     * @see #leastSquaresMovingAverage(Indicator, int)
     */
    public static LeastSquaresMovingAverage lsma(Indicator<Num> indicator, int length) {
        return leastSquaresMovingAverage(indicator, length);
    }

    /**
     * Convenience static method for {@link #LeastSquaresMovingAverage(Indicator, int)}.
     */
    public static LeastSquaresMovingAverage leastSquaresMovingAverage(Indicator<Num> indicator, int length) {
        return new LeastSquaresMovingAverage(indicator, length);
    }

    private final Indicator<LinearRegressionResult> linearRegression;

    /**
     * Instantiates a new {@link LeastSquaresMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public LeastSquaresMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        linearRegression = new LinearRegression(indicator, Set.of(Y), length);
    }

    @Override
    protected Num calculate(long index) {
        return linearRegression.getValue(index).getY();
    }
}
