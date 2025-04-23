package trade.invision.indicators.indicators.ad;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.RecursiveIndicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.clv.CloseLocationValue;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link AccumulationDistribution} is a {@link Num} {@link Indicator} to provide the Accumulation/Distribution (AD) of
 * a {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/a/accumulationdistribution.asp">Investopedia</a>
 */
public class AccumulationDistribution extends RecursiveIndicator<Num> {

    /**
     * @see #accumulationDistribution(BarSeries)
     */
    public static AccumulationDistribution ad(BarSeries barSeries) {
        return accumulationDistribution(barSeries);
    }

    /**
     * Convenience static method for {@link #AccumulationDistribution(BarSeries)}.
     */
    public static AccumulationDistribution accumulationDistribution(BarSeries barSeries) {
        return new AccumulationDistribution(barSeries);
    }

    private final CloseLocationValue clv;
    private final Volume volume;

    /**
     * Instantiates a new {@link AccumulationDistribution}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public AccumulationDistribution(BarSeries barSeries) {
        super(barSeries, 1);
        clv = new CloseLocationValue(barSeries);
        volume = new Volume(barSeries);
    }

    @Override
    protected Num calculate(long index) {
        final Num mfv = clv.getValue(index).multiply(volume.getValue(index));
        if (index == 0) {
            return mfv;
        }
        return mfv.add(getValue(index - 1));
    }
}
