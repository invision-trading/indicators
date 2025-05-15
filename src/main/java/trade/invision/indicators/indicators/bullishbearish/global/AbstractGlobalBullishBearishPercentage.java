package trade.invision.indicators.indicators.bullishbearish.global;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.RecursiveIndicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import java.util.function.Predicate;

/**
 * {@link AbstractGlobalBullishBearishPercentage} is an abstract {@link Num} {@link Indicator} for global
 * bullish/bearish percentage {@link Indicator}s.
 */
abstract class AbstractGlobalBullishBearishPercentage extends RecursiveIndicator<Num> {

    private final BarSeries barSeries;
    private final Predicate<Bar> predicate;

    /**
     * Instantiates a new {@link AbstractGlobalBullishBearishPercentage}.
     *
     * @param barSeries the {@link BarSeries}
     * @param bullish   <code>true</code> for {@link Bar#isBullish()}, <code>false</code> for {@link Bar#isBearish()}
     */
    protected AbstractGlobalBullishBearishPercentage(BarSeries barSeries, boolean bullish) {
        super(barSeries, 0);
        this.barSeries = barSeries;
        predicate = bullish ? Bar::isBullish : Bar::isBearish;
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return predicate.test(barSeries.get(index)) ? numOfOne() : numOfZero();
        }
        final Num previousValue = getValue(index - 1);
        Num count = previousValue.multiply(index);
        if (predicate.test(barSeries.get(index))) {
            count = count.increment();
        }
        return count.divide(index + 1);
    }
}
