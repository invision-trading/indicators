package trade.invision.indicators.indicators.bar;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

import java.time.temporal.ChronoUnit;

import static java.time.ZoneOffset.UTC;

/**
 * {@link BarTimespan} is a {@link Num} {@link Indicator} to provide {@link Bar#getTimespan()} in a specified
 * {@link ChronoUnit}.
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
public class BarTimespan extends Indicator<Num> {

    /**
     * Convenience static method for {@link #BarTimespan(BarSeries, ChronoUnit)}.
     */
    public static BarTimespan barTimespan(BarSeries barSeries, ChronoUnit chronoUnit) {
        return new BarTimespan(barSeries, chronoUnit);
    }

    private final BarSeries barSeries;
    private final ChronoUnit unit;

    /**
     * Instantiates a new {@link BarTimespan}.
     *
     * @param barSeries the {@link BarSeries}
     * @param unit      the {@link ChronoUnit}
     */
    public BarTimespan(BarSeries barSeries, ChronoUnit unit) {
        super(barSeries, 0);
        this.barSeries = barSeries;
        this.unit = unit;
    }

    @Override
    protected Num calculate(long index) {
        final Bar bar = barSeries.get(index);
        return numOf(unit.between(bar.getEnd().atOffset(UTC), bar.getStart().atOffset(UTC)));
    }
}
