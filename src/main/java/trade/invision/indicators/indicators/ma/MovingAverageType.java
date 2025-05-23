package trade.invision.indicators.indicators.ma;

import trade.invision.indicators.indicators.ma.ema.DoubleExponentialMovingAverage;
import trade.invision.indicators.indicators.ma.ema.ExponentialMovingAverage;
import trade.invision.indicators.indicators.ma.ema.TripleExponentialMovingAverage;
import trade.invision.indicators.indicators.ma.ema.WellesWilderMovingAverage;
import trade.invision.indicators.indicators.ma.ema.ZeroLagExponentialMovingAverage;
import trade.invision.indicators.indicators.ma.hma.HullMovingAverage;
import trade.invision.indicators.indicators.ma.kama.KaufmansAdaptiveMovingAverage;
import trade.invision.indicators.indicators.ma.lsma.LeastSquaresMovingAverage;
import trade.invision.indicators.indicators.ma.lsma.PredictiveLeastSquaresMovingAverage;
import trade.invision.indicators.indicators.ma.sma.SimpleMovingAverage;
import trade.invision.indicators.indicators.ma.wma.LinearlyWeightedMovingAverage;
import trade.invision.indicators.indicators.ma.wma.WeightedMovingAverage;

/**
 * {@link MovingAverageType} defines moving average type enums for {@link MovingAverageSupplier}.
 */
public enum MovingAverageType {

    /**
     * @see SimpleMovingAverage
     */
    SIMPLE,

    /**
     * @see ExponentialMovingAverage
     */
    EXPONENTIAL,

    /**
     * @see DoubleExponentialMovingAverage
     */
    DOUBLE_EXPONENTIAL,

    /**
     * @see TripleExponentialMovingAverage
     */
    TRIPLE_EXPONENTIAL,

    /**
     * @see WellesWilderMovingAverage
     */
    WELLES_WILDER,

    /**
     * @see ZeroLagExponentialMovingAverage
     */
    ZERO_LAG_EXPONENTIAL,

    /**
     * @see HullMovingAverage
     */
    HULL,

    /**
     * @see KaufmansAdaptiveMovingAverage
     */
    KAUFMANS_ADAPTIVE,

    /**
     * @see LeastSquaresMovingAverage
     */
    LEAST_SQUARES,

    /**
     * @see PredictiveLeastSquaresMovingAverage
     */
    PREDICTIVE_LEAST_SQUARES,

    /**
     * @see WeightedMovingAverage
     */
    WEIGHTED,

    /**
     * @see LinearlyWeightedMovingAverage
     */
    LINEARLY_WEIGHTED
}
