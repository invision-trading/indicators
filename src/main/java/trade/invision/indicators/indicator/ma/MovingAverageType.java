package trade.invision.indicators.indicator.ma;

import trade.invision.indicators.indicator.ma.ema.DoubleExponentialMovingAverage;
import trade.invision.indicators.indicator.ma.ema.ExponentialMovingAverage;
import trade.invision.indicators.indicator.ma.ema.TripleExponentialMovingAverage;
import trade.invision.indicators.indicator.ma.ema.WellesWilderMovingAverage;
import trade.invision.indicators.indicator.ma.ema.ZeroLagExponentialMovingAverage;
import trade.invision.indicators.indicator.ma.hma.HullMovingAverage;
import trade.invision.indicators.indicator.ma.kama.KaufmansAdaptiveMovingAverage;
import trade.invision.indicators.indicator.ma.lsma.LeastSquaresMovingAverage;
import trade.invision.indicators.indicator.ma.lsma.PredictiveLeastSquaresMovingAverage;
import trade.invision.indicators.indicator.ma.sma.SimpleMovingAverage;
import trade.invision.indicators.indicator.ma.wma.LinearlyWeightedMovingAverage;
import trade.invision.indicators.indicator.ma.wma.WeightedMovingAverage;

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
