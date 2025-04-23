package trade.invision.indicators.indicators.mf;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.barprice.Hlc3;
import trade.invision.indicators.series.bar.Bar;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link MoneyFlow} is a {@link Num} {@link Indicator} to provide the Money Flow (MF) of a {@link Bar}.
 *
 * @see <a href="https://www.investopedia.com/terms/m/moneyflow.asp">Investopedia</a>
 */
public class MoneyFlow extends Indicator<Num> {

    /**
     * @see #moneyFlow(BarSeries)
     */
    public static MoneyFlow mf(BarSeries barSeries) {
        return moneyFlow(barSeries);
    }

    /**
     * Convenience static method for {@link #MoneyFlow(BarSeries)}.
     */
    public static MoneyFlow moneyFlow(BarSeries barSeries) {
        return new MoneyFlow(barSeries);
    }

    private final Hlc3 hlc3;
    private final Volume volume;

    /**
     * Instantiates a new {@link MoneyFlow}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public MoneyFlow(BarSeries barSeries) {
        super(barSeries, 0);
        hlc3 = new Hlc3(barSeries);
        volume = new Volume(barSeries);
    }

    @Override
    protected Num calculate(long index) {
        return hlc3.getValue(index).multiply(volume.getValue(index));
    }
}
