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
    SMA,

    /**
     * @see ExponentialMovingAverage
     */
    EMA,

    /**
     * @see DoubleExponentialMovingAverage
     */
    DEMA,

    /**
     * @see TripleExponentialMovingAverage
     */
    TEMA,

    /**
     * @see WellesWilderMovingAverage
     */
    WWMA,

    /**
     * @see ZeroLagExponentialMovingAverage
     */
    ZLEMA,

    /**
     * @see HullMovingAverage
     */
    HMA,

    /**
     * @see KaufmansAdaptiveMovingAverage
     */
    KAMA,

    /**
     * @see LeastSquaresMovingAverage
     */
    LSMA,

    /**
     * @see PredictiveLeastSquaresMovingAverage
     */
    PLSMA,

    /**
     * @see WeightedMovingAverage
     */
    WMA,

    /**
     * @see LinearlyWeightedMovingAverage
     */
    LWMA
}
