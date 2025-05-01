package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.CachelessIndicator;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link Volume} is a {@link Num} {@link Indicator} to provide {@link Bar#getVolume()}.
 *
 * @see <a href="https://www.investopedia.com/terms/v/volume.asp">Investopedia</a>
 */
public class Volume extends CachelessIndicator<Num> {

    /**
     * Gets a {@link Volume}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public static Volume volume(BarSeries barSeries) {
        return new Volume(barSeries);
    }

    private final BarSeries barSeries;

    protected Volume(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        return barSeries.get(index).getVolume();
    }
}
