package trade.invision.indicators.indicators.ma;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.jetbrains.annotations.Nullable;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.ma.ema.DoubleExponentialMovingAverage;
import trade.invision.indicators.indicators.ma.ema.ExponentialMovingAverage;
import trade.invision.indicators.indicators.ma.ema.TripleExponentialMovingAverage;
import trade.invision.indicators.indicators.ma.kama.KaufmansAdaptiveMovingAverage;
import trade.invision.num.Num;

import static trade.invision.indicators.indicators.ma.MovingAverageType.*;
import static trade.invision.indicators.indicators.ma.ema.DoubleExponentialMovingAverage.dema;
import static trade.invision.indicators.indicators.ma.ema.ExponentialMovingAverage.ema;
import static trade.invision.indicators.indicators.ma.ema.TripleExponentialMovingAverage.tema;
import static trade.invision.indicators.indicators.ma.ema.WellesWilderMovingAverage.wwma;
import static trade.invision.indicators.indicators.ma.ema.ZeroLagExponentialMovingAverage.zlema;
import static trade.invision.indicators.indicators.ma.hma.HullMovingAverage.hma;
import static trade.invision.indicators.indicators.ma.kama.KaufmansAdaptiveMovingAverage.kama;
import static trade.invision.indicators.indicators.ma.lsma.LeastSquaresMovingAverage.lsma;
import static trade.invision.indicators.indicators.ma.lsma.PredictiveLeastSquaresMovingAverage.plsma;
import static trade.invision.indicators.indicators.ma.sma.SimpleMovingAverage.sma;
import static trade.invision.indicators.indicators.ma.wma.LinearlyWeightedMovingAverage.lwma;
import static trade.invision.indicators.indicators.ma.wma.WeightedMovingAverage.wma;

/**
 * {@link MovingAverageSupplier} supplies a moving average {@link Indicator} configured with the given options.
 */
@Value @AllArgsConstructor @Builder(toBuilder = true)
public class MovingAverageSupplier {

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #simpleBuilder()}.
     */
    public static MovingAverageSupplier simple() {
        return simpleBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to {@link MovingAverageType#SIMPLE}.
     */
    public static MovingAverageSupplierBuilder simpleBuilder() {
        return MovingAverageSupplier.builder().type(SIMPLE);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #exponentialBuilder()}.
     */
    public static MovingAverageSupplier exponential() {
        return exponentialBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to
     * {@link MovingAverageType#EXPONENTIAL}.
     */
    public static MovingAverageSupplierBuilder exponentialBuilder() {
        return MovingAverageSupplier.builder().type(EXPONENTIAL);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #doubleExponentialBuilder()}.
     */
    public static MovingAverageSupplier doubleExponential() {
        return doubleExponentialBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to
     * {@link MovingAverageType#DOUBLE_EXPONENTIAL}.
     */
    public static MovingAverageSupplierBuilder doubleExponentialBuilder() {
        return MovingAverageSupplier.builder().type(DOUBLE_EXPONENTIAL);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #tripleExponentialBuilder()}.
     */
    public static MovingAverageSupplier tripleExponential() {
        return tripleExponentialBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to
     * {@link MovingAverageType#TRIPLE_EXPONENTIAL}.
     */
    public static MovingAverageSupplierBuilder tripleExponentialBuilder() {
        return MovingAverageSupplier.builder().type(TRIPLE_EXPONENTIAL);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #wellesWilderBuilder()}.
     */
    public static MovingAverageSupplier wellesWilder() {
        return wellesWilderBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to
     * {@link MovingAverageType#WELLES_WILDER}.
     */
    public static MovingAverageSupplierBuilder wellesWilderBuilder() {
        return MovingAverageSupplier.builder().type(WELLES_WILDER);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #zeroLagExponentialBuilder()}.
     */
    public static MovingAverageSupplier zeroLagExponential() {
        return zeroLagExponentialBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to
     * {@link MovingAverageType#ZERO_LAG_EXPONENTIAL}.
     */
    public static MovingAverageSupplierBuilder zeroLagExponentialBuilder() {
        return MovingAverageSupplier.builder().type(ZERO_LAG_EXPONENTIAL);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #hullBuilder()}.
     */
    public static MovingAverageSupplier hull() {
        return hullBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to {@link MovingAverageType#HULL}.
     */
    public static MovingAverageSupplierBuilder hullBuilder() {
        return MovingAverageSupplier.builder().type(HULL);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #kaufmansAdaptiveBuilder()}.
     */
    public static MovingAverageSupplier kaufmansAdaptive() {
        return kaufmansAdaptiveBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to
     * {@link MovingAverageType#KAUFMANS_ADAPTIVE}.
     */
    public static MovingAverageSupplierBuilder kaufmansAdaptiveBuilder() {
        return MovingAverageSupplier.builder().type(KAUFMANS_ADAPTIVE);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #leastSquaresBuilder()}.
     */
    public static MovingAverageSupplier leastSquares() {
        return leastSquaresBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to
     * {@link MovingAverageType#LEAST_SQUARES}.
     */
    public static MovingAverageSupplierBuilder leastSquaresBuilder() {
        return MovingAverageSupplier.builder().type(LEAST_SQUARES);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #predictiveLeastSquaresBuilder()}.
     */
    public static MovingAverageSupplier predictiveLeastSquares() {
        return predictiveLeastSquaresBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to
     * {@link MovingAverageType#PREDICTIVE_LEAST_SQUARES}.
     */
    public static MovingAverageSupplierBuilder predictiveLeastSquaresBuilder() {
        return MovingAverageSupplier.builder().type(PREDICTIVE_LEAST_SQUARES);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #weightedBuilder()}.
     */
    public static MovingAverageSupplier weighted() {
        return weightedBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to
     * {@link MovingAverageType#WEIGHTED}.
     */
    public static MovingAverageSupplierBuilder weightedBuilder() {
        return MovingAverageSupplier.builder().type(WEIGHTED);
    }

    /**
     * Calls {@link MovingAverageSupplierBuilder#build()} on {@link #linearlyWeightedBuilder()}.
     */
    public static MovingAverageSupplier linearlyWeighted() {
        return linearlyWeightedBuilder().build();
    }

    /**
     * Creates a {@link MovingAverageSupplierBuilder} with {@link #getType()} set to
     * {@link MovingAverageType#LINEARLY_WEIGHTED}.
     */
    public static MovingAverageSupplierBuilder linearlyWeightedBuilder() {
        return MovingAverageSupplier.builder().type(LINEARLY_WEIGHTED);
    }

    /**
     * The {@link MovingAverageType}.
     */
    MovingAverageType type;
    /**
     * The <code>smoothing</code> argument for {@link ExponentialMovingAverage}, {@link DoubleExponentialMovingAverage},
     * and {@link TripleExponentialMovingAverage}.
     */
    @Nullable Num smoothing;
    /**
     * The <code>fastLength</code> argument for {@link KaufmansAdaptiveMovingAverage}.
     */
    @Nullable Integer fastLength;
    /**
     * The <code>slowLength</code> argument for {@link KaufmansAdaptiveMovingAverage}.
     */
    @Nullable Integer slowLength;

    /**
     * Creates a moving average {@link Num} {@link Indicator} of the given {@link #getType()} and configured with the
     * given options.
     *
     * @param indicator the {@link Num} {@link Indicator} to apply the moving average to
     * @param length    the moving average length
     *
     * @return the moving average {@link Num} {@link Indicator}
     */
    public Indicator<Num> supply(Indicator<Num> indicator, int length) {
        return switch (type) {
            case SIMPLE -> sma(indicator, length);
            case EXPONENTIAL -> smoothing != null ? ema(indicator, length, smoothing) : ema(indicator, length);
            case DOUBLE_EXPONENTIAL -> smoothing != null ? dema(indicator, length, smoothing) : dema(indicator, length);
            case TRIPLE_EXPONENTIAL -> smoothing != null ? tema(indicator, length, smoothing) : tema(indicator, length);
            case WELLES_WILDER -> wwma(indicator, length);
            case ZERO_LAG_EXPONENTIAL -> zlema(indicator, length);
            case HULL -> hma(indicator, length);
            case KAUFMANS_ADAPTIVE -> slowLength != null && fastLength != null ?
                    kama(indicator, length, fastLength, slowLength) : kama(indicator, length);
            case LEAST_SQUARES -> lsma(indicator, length);
            case PREDICTIVE_LEAST_SQUARES -> plsma(indicator, length);
            case WEIGHTED -> wma(indicator, length);
            case LINEARLY_WEIGHTED -> lwma(indicator, length);
        };
    }
}
