package trade.invision.indicators.indicators.ma.lsma;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.statistical.regression.LinearRegression;
import trade.invision.indicators.indicators.statistical.regression.LinearRegressionResult;
import trade.invision.indicators.indicators.statistical.regression.LinearRegressionResultType;
import trade.invision.num.Num;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static trade.invision.indicators.indicators.statistical.regression.LinearRegressionResultType.NEXT_Y;

/**
 * {@link PredictiveLeastSquaresMovingAverage} is a {@link Num} {@link Indicator} to provide a predictive Least Squares
 * Moving Average (PLSMA) over a <code>length</code> of values. This is the same as {@link LeastSquaresMovingAverage},
 * but with {@link LinearRegressionResultType#NEXT_Y}.
 *
 * @see <a href="https://www.fmlabs.com/reference/default.htm?url=LstSqrMA.htm">FM Labs</a>
 */
public class PredictiveLeastSquaresMovingAverage extends Indicator<Num> {

    /**
     * @see #predictiveLeastSquaresMovingAverage(Indicator, int)
     */
    public static PredictiveLeastSquaresMovingAverage plsma(Indicator<Num> indicator, int length) {
        return predictiveLeastSquaresMovingAverage(indicator, length);
    }

    /**
     * Convenience static method for {@link #PredictiveLeastSquaresMovingAverage(Indicator, int)}.
     */
    public static PredictiveLeastSquaresMovingAverage predictiveLeastSquaresMovingAverage(Indicator<Num> indicator,
            int length) {
        return new PredictiveLeastSquaresMovingAverage(indicator, length);
    }

    private final Indicator<LinearRegressionResult> linearRegression;

    /**
     * Instantiates a new {@link PredictiveLeastSquaresMovingAverage}.
     *
     * @param indicator the {@link Indicator}
     * @param length    the number of values to look back at
     */
    public PredictiveLeastSquaresMovingAverage(Indicator<Num> indicator, int length) {
        super(indicator.getSeries(), length);
        checkArgument(length > 0, "'length' must be greater than zero!");
        linearRegression = new LinearRegression(indicator, Set.of(NEXT_Y), length);
    }

    @Override
    protected Num calculate(long index) {
        return linearRegression.getValue(index).getNextY();
    }
}
