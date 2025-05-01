package trade.invision.indicators.indicator.nvi;

import trade.invision.indicators.indicator.Indicator;
import trade.invision.indicators.indicator.RecursiveIndicator;
import trade.invision.indicators.indicator.closeprice.ClosePricePercentChange;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import java.util.function.BiPredicate;

import static trade.invision.indicators.indicator.bar.Volume.volume;
import static trade.invision.indicators.indicator.closeprice.ClosePricePercentChange.closePricePercentChange;
import static trade.invision.indicators.indicator.previous.PreviousValue.previousValue;

/**
 * {@link AbstractPositiveNegativeVolumeIndex} is an abstract {@link Num} {@link Indicator} for Positive/Negative Volume
 * Index {@link Indicator}s.
 */
abstract class AbstractPositiveNegativeVolumeIndex extends RecursiveIndicator<Num> {

    private final Indicator<Num> volume;
    private final Indicator<Num> previousVolume;
    private final ClosePricePercentChange closePricePercentChange;
    private final BiPredicate<Num, Num> predicate;

    /**
     * Instantiates a new {@link AbstractPositiveNegativeVolumeIndex}.
     *
     * @param barSeries the {@link BarSeries}
     * @param positive  <code>true</code> for positive, <code>false</code> for negative
     */
    protected AbstractPositiveNegativeVolumeIndex(BarSeries barSeries, boolean positive) {
        super(barSeries, 1);
        volume = volume(barSeries);
        previousVolume = previousValue(volume);
        closePricePercentChange = closePricePercentChange(barSeries);
        predicate = positive ? Num::isGreaterThan : Num::isLessThan;
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return numOfHundred();
        }
        Num previousValue = getValue(index - 1);
        if (predicate.test(volume.getValue(index), previousVolume.getValue(index))) {
            previousValue = previousValue.add(closePricePercentChange.getValue(index).multiply(previousValue));
        }
        return previousValue;
    }
}
