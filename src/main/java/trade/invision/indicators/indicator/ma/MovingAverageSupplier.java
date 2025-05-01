package trade.invision.indicators.indicator.ma;

import lombok.Builder;
import lombok.Value;
import org.jetbrains.annotations.Nullable;
import trade.invision.indicators.indicator.Indicator;
import trade.invision.num.Num;

import static trade.invision.indicators.indicator.ma.MovingAverageType.*;
import static trade.invision.indicators.indicator.ma.ema.DoubleExponentialMovingAverage.dema;
import static trade.invision.indicators.indicator.ma.ema.ExponentialMovingAverage.ema;
import static trade.invision.indicators.indicator.ma.ema.TripleExponentialMovingAverage.tema;
import static trade.invision.indicators.indicator.ma.ema.WellesWilderMovingAverage.wwma;
import static trade.invision.indicators.indicator.ma.ema.ZeroLagExponentialMovingAverage.zlema;
import static trade.invision.indicators.indicator.ma.hma.HullMovingAverage.hma;
import static trade.invision.indicators.indicator.ma.kama.KaufmansAdaptiveMovingAverage.kama;
import static trade.invision.indicators.indicator.ma.lsma.LeastSquaresMovingAverage.lsma;
import static trade.invision.indicators.indicator.ma.lsma.PredictiveLeastSquaresMovingAverage.plsma;
import static trade.invision.indicators.indicator.ma.sma.SimpleMovingAverage.sma;
import static trade.invision.indicators.indicator.ma.wma.LinearlyWeightedMovingAverage.lwma;
import static trade.invision.indicators.indicator.ma.wma.WeightedMovingAverage.wma;

/**
 * {@link MovingAverageSupplier} supplies a moving average {@link Indicator} configured with the given options.
 */
@Value @Builder(toBuilder = true)
public class MovingAverageSupplier {

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#SMA}.
     */
    public static MovingAverageSupplier smaSupplier() {
        return MovingAverageSupplier.builder().type(SMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#EMA}.
     */
    public static MovingAverageSupplier emaSupplier() {
        return MovingAverageSupplier.builder().type(EMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#DEMA}.
     */
    public static MovingAverageSupplier demaSupplier() {
        return MovingAverageSupplier.builder().type(DEMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#TEMA}.
     */
    public static MovingAverageSupplier temaSupplier() {
        return MovingAverageSupplier.builder().type(TEMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#WWMA}.
     */
    public static MovingAverageSupplier wwmaSupplier() {
        return MovingAverageSupplier.builder().type(WWMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#ZLEMA}.
     */
    public static MovingAverageSupplier zlemaSupplier() {
        return MovingAverageSupplier.builder().type(ZLEMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#HMA}.
     */
    public static MovingAverageSupplier hmaSupplier() {
        return MovingAverageSupplier.builder().type(HMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#KAMA}.
     */
    public static MovingAverageSupplier kamaSupplier() {
        return MovingAverageSupplier.builder().type(KAMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#LSMA}.
     */
    public static MovingAverageSupplier lsmaSupplier() {
        return MovingAverageSupplier.builder().type(LSMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#PLSMA}.
     */
    public static MovingAverageSupplier plsmaSupplier() {
        return MovingAverageSupplier.builder().type(PLSMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#WMA}.
     */
    public static MovingAverageSupplier wmaSupplier() {
        return MovingAverageSupplier.builder().type(WMA).build();
    }

    /**
     * Creates a {@link MovingAverageSupplier} with {@link #getType()} set to {@link MovingAverageType#LWMA}.
     */
    public static MovingAverageSupplier lwmaSupplier() {
        return MovingAverageSupplier.builder().type(LWMA).build();
    }

    MovingAverageType type;
    @Nullable Num emaSmoothing;
    @Nullable Num demaSmoothing;
    @Nullable Num temaSmoothing;
    @Nullable Integer kamaFastLength;
    @Nullable Integer kamaSlowLength;

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
            case SMA -> sma(indicator, length);
            case EMA -> emaSmoothing != null ? ema(indicator, length, emaSmoothing) : ema(indicator, length);
            case DEMA -> demaSmoothing != null ? dema(indicator, length, demaSmoothing) : dema(indicator, length);
            case TEMA -> temaSmoothing != null ? tema(indicator, length, temaSmoothing) : tema(indicator, length);
            case WWMA -> wwma(indicator, length);
            case ZLEMA -> zlema(indicator, length);
            case HMA -> hma(indicator, length);
            case KAMA -> kamaSlowLength != null && kamaFastLength != null ?
                    kama(indicator, length, kamaFastLength, kamaSlowLength) : kama(indicator, length);
            case LSMA -> lsma(indicator, length);
            case PLSMA -> plsma(indicator, length);
            case WMA -> wma(indicator, length);
            case LWMA -> lwma(indicator, length);
        };
    }
}
