package trade.invision.indicators.indicators.iii;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link IntradayIntensityIndex} is a {@link Num} {@link Indicator} to provide the Intraday Intensity Index (III) of a
 * {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/i/intradayintensityindex.asp">Investopedia</a>
 */
public class IntradayIntensityIndex extends Indicator<Num> {

    /**
     * @see #intradayIntensityIndex(BarSeries)
     */
    public static IntradayIntensityIndex iii(BarSeries barSeries) {
        return intradayIntensityIndex(barSeries);
    }

    /**
     * Convenience static method for {@link #IntradayIntensityIndex(BarSeries)}.
     */
    public static IntradayIntensityIndex intradayIntensityIndex(BarSeries barSeries) {
        return new IntradayIntensityIndex(barSeries);
    }

    private final BarSeries barSeries;

    /**
     * Instantiates a new {@link IntradayIntensityIndex}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public IntradayIntensityIndex(BarSeries barSeries) {
        super(barSeries, 0);
        this.barSeries = barSeries;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        final Num close = bar.getClose();
        final Num high = bar.getHigh();
        final Num low = bar.getLow();
        final Num volume = bar.getVolume();
        return numOfTwo().multiply(close).subtract(high).subtract(low).divide(high.subtract(low).multiply(volume));
    }
}
