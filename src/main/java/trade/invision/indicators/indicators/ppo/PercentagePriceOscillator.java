package trade.invision.indicators.indicators.ppo;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.num.Num;

import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link PercentagePriceOscillator} is a {@link Num} {@link Indicator} to provide the Percentage Price Oscillator (PPO)
 * over a <code>shortLength</code> and <code>longLength</code> of values. This is the same as the Range Action
 * Verification Index (RAVI).
 *
 * @see <a href="https://www.investopedia.com/terms/p/ppo.asp">Investopedia</a>
 */
public class PercentagePriceOscillator extends Indicator<Num> {

    /**
     * @see #percentagePriceOscillator(Indicator, int, int, BiFunction)
     */
    public static PercentagePriceOscillator ppo(Indicator<Num> indicator,
            int shortLength, int longLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return percentagePriceOscillator(indicator, shortLength, longLength, averagingIndicatorSupplier);
    }

    /**
     * Convenience static method for {@link #PercentagePriceOscillator(Indicator, int, int, BiFunction)}.
     */
    public static PercentagePriceOscillator percentagePriceOscillator(Indicator<Num> indicator,
            int shortLength, int longLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        return new PercentagePriceOscillator(indicator, shortLength, longLength, averagingIndicatorSupplier);
    }

    private final Indicator<Num> shortAverage;
    private final Indicator<Num> longAverage;

    /**
     * Instantiates a new {@link PercentagePriceOscillator}.
     *
     * @param indicator                  the {@link Indicator}
     * @param shortLength                the short averaging length (typically 12)
     * @param longLength                 the long averaging length (typically 26)
     * @param averagingIndicatorSupplier the {@link BiFunction} to supply the averaging {@link Indicator}
     */
    public PercentagePriceOscillator(Indicator<Num> indicator, int shortLength, int longLength,
            BiFunction<Indicator<Num>, Integer, Indicator<Num>> averagingIndicatorSupplier) {
        super(indicator.getSeries(), max(shortLength, longLength));
        checkArgument(shortLength > 0, "'shortLength' must be greater than zero!");
        checkArgument(longLength > 0, "'longLength' must be greater than zero!");
        shortAverage = averagingIndicatorSupplier.apply(indicator, shortLength);
        longAverage = averagingIndicatorSupplier.apply(indicator, longLength);
    }

    @Override
    protected Num calculate(long index) {
        return shortAverage.getValue(index).divide(longAverage.getValue(index)).decrement().multiply(numOfHundred());
    }
}
