package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;

/**
 * {@link Overlaps} is a {@link Boolean} {@link Indicator} to provide {@link Bar#overlaps(Bar)}.
 */
public class Overlaps extends Indicator<Boolean> {

    /**
     * Convenience static method for {@link #Overlaps(BarSeries, BarSeries)}.
     */
    public static Overlaps overlaps(BarSeries barSeries, BarSeries check) {
        return new Overlaps(barSeries, check);
    }

    private final BarSeries barSeries;
    private final BarSeries check;

    /**
     * Instantiates a new {@link Overlaps}.
     *
     * @param barSeries the {@link BarSeries}
     * @param check     the check {@link BarSeries}
     */
    public Overlaps(BarSeries barSeries, BarSeries check) {
        super(barSeries, 0);
        this.barSeries = barSeries;
        this.check = check;
    }

    @Override
    protected Boolean calculate(long index) {
        return barSeries.get(index).overlaps(check.get(index));
    }
}
