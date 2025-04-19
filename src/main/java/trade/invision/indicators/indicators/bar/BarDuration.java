package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import java.time.temporal.ChronoUnit;

import static java.time.ZoneOffset.UTC;

/**
 * {@link BarDuration} is a {@link Num} {@link Indicator} to provide {@link Bar#getDuration()} in a specified
 * {@link ChronoUnit}.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class BarDuration extends Indicator<Num> {

    /**
     * Convenience static method for {@link #BarDuration(BarSeries, ChronoUnit)}.
     */
    public static BarDuration barDuration(BarSeries barSeries, ChronoUnit chronoUnit) {
        return new BarDuration(barSeries, chronoUnit);
    }

    private final BarSeries barSeries;
    private final ChronoUnit unit;

    /**
     * Instantiates a new {@link BarDuration}.
     *
     * @param barSeries the {@link BarSeries}
     * @param unit      the {@link ChronoUnit}
     */
    public BarDuration(BarSeries barSeries, ChronoUnit unit) {
        super(barSeries, 0);
        this.barSeries = barSeries;
        this.unit = unit;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return numOf(unit.between(bar.getEnd().atZone(UTC), bar.getStart().atZone(UTC)));
    }
}
