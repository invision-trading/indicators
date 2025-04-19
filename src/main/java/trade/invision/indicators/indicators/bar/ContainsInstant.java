package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;

import java.time.Instant;

/**
 * {@link ContainsInstant} is a {@link Boolean} {@link Indicator} to provide {@link Bar#containsInstant(Instant)} for
 * {@link Instant}s.
 */
public class ContainsInstant extends Indicator<Boolean> {

    /**
     * Convenience static method for {@link #ContainsInstant(BarSeries, Indicator)}.
     */
    public static ContainsInstant containsInstant(BarSeries barSeries, Indicator<Instant> check) {
        return new ContainsInstant(barSeries, check);
    }

    private final BarSeries barSeries;
    private final Indicator<Instant> check;

    /**
     * Instantiates a new {@link ContainsInstant}.
     *
     * @param barSeries the {@link BarSeries}
     * @param check     the check {@link Indicator}
     */
    public ContainsInstant(BarSeries barSeries, Indicator<Instant> check) {
        super(barSeries, 0);
        this.barSeries = barSeries;
        this.check = check;
    }

    @Override
    protected Boolean calculate(long index) {
        return barSeries.get(index).containsInstant(check.getValue(index));
    }
}
