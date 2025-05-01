package trade.invision.indicators.indicator.bullishbearish.local;

import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;

/**
 * {@link AbstractLocalBullishBearishPercentage} is an abstract {@link Num} {@link Indicator} for local bullish/bearish
 * percentage {@link Indicator}s.
 */
abstract class AbstractLocalBullishBearishPercentage extends Indicator<Num> {

    private final BarSeries barSeries;
    private final int length;
    private final Predicate<Bar> predicate;

    /**
     * Instantiates a new {@link AbstractLocalBullishBearishPercentage}.
     *
     * @param barSeries the {@link BarSeries}
     * @param length    the number of values to look back at
     * @param bullish   <code>true</code> for {@link Bar#isBullish()}, <code>false</code> for {@link Bar#isBearish()}
     */
    protected AbstractLocalBullishBearishPercentage(BarSeries barSeries, int length, boolean bullish) {
        super(barSeries, 0);
        checkArgument(length > 0, "'length' must be greater than zero!");
        this.barSeries = barSeries;
        this.length = length;
        predicate = bullish ? Bar::isBullish : Bar::isBearish;
    }

    @Override
    protected Num calculate(long index) {
        // TODO this can be optimized similar to 'CumulativeSum'.
        final long startIndex = max(0, index - length + 1);
        final long observations = index - startIndex + 1;
        int count = 0;
        for (long seriesIndex = startIndex; seriesIndex <= index; seriesIndex++) {
            if (predicate.test(barSeries.get(seriesIndex))) {
                count++;
            }
        }
        return numOf(count).divide(observations);
    }
}
