package trade.invision.indicators.indicators.tsv;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.closeprice.ClosePriceDifference;
import trade.invision.indicators.indicators.cumulative.CumulativeSum;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link TimeSegmentedVolume} is a {@link Num} {@link Indicator} to provide the Time Segmented Volume (TSV) over a
 * <code>length</code> of {@link Bar}s.
 *
 * @see <a href="https://www.investopedia.com/terms/t/tsv.asp">Investopedia</a>
 */
public class TimeSegmentedVolume extends Indicator<Num> {

    /**
     * @see #timeSegmentedVolume(BarSeries, int)
     */
    public static TimeSegmentedVolume tsv(BarSeries barSeries, int length) {
        return timeSegmentedVolume(barSeries, length);
    }

    /**
     * Convenience static method for {@link #TimeSegmentedVolume(BarSeries, int)}.
     */
    public static TimeSegmentedVolume timeSegmentedVolume(BarSeries barSeries, int length) {
        return new TimeSegmentedVolume(barSeries, length);
    }

    private final Indicator<Num> tsv;

    /**
     * Instantiates a new {@link TimeSegmentedVolume}.
     *
     * @param barSeries the {@link BarSeries}
     * @param length    the number of values to look back at
     */
    public TimeSegmentedVolume(BarSeries barSeries, int length) {
        super(barSeries, length - 1);
        checkArgument(length > 0, "'length' must be greater than zero!");
        final ClosePriceDifference closePriceDifference = new ClosePriceDifference(barSeries);
        final Volume volume = new Volume(barSeries);
        tsv = new CumulativeSum(new Indicator<>(series, 0) {
            @Override
            protected Num calculate(long index) {
                return closePriceDifference.getValue(index).multiply(volume.getValue(index));
            }
        }, length);
    }

    @Override
    protected Num calculate(long index) {
        return tsv.getValue(index);
    }
}
