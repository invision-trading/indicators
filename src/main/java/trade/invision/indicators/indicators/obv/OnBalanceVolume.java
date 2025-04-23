package trade.invision.indicators.indicators.obv;

import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.RecursiveIndicator;
import trade.invision.indicators.indicators.bar.Close;
import trade.invision.indicators.indicators.bar.Volume;
import trade.invision.indicators.indicators.previous.PreviousValue;
import trade.invision.indicators.series.bar.BarSeries;
import trade.invision.num.Num;

/**
 * {@link OnBalanceVolume} is a {@link Num} {@link Indicator} to provide the On-Balance Volume (OBV).
 *
 * @see <a href="https://www.investopedia.com/terms/o/onbalancevolume.asp">Investopedia</a>
 */
public class OnBalanceVolume extends RecursiveIndicator<Num> {

    /**
     * @see #onBalanceVolume(BarSeries)
     */
    public static OnBalanceVolume obv(BarSeries barSeries) {
        return onBalanceVolume(barSeries);
    }

    /**
     * Convenience static method for {@link #OnBalanceVolume(BarSeries)}.
     */
    public static OnBalanceVolume onBalanceVolume(BarSeries barSeries) {
        return new OnBalanceVolume(barSeries);
    }

    private final Close close;
    private final Indicator<Num> previousClose;
    private final Indicator<Num> volume;

    /**
     * Instantiates a new {@link OnBalanceVolume}.
     *
     * @param barSeries the {@link BarSeries}
     */
    public OnBalanceVolume(BarSeries barSeries) {
        super(barSeries, 1);
        volume = new Volume(barSeries);
        close = new Close(barSeries);
        previousClose = new PreviousValue<>(close);
    }

    @Override
    protected Num calculate(long index) {
        if (index == 0) {
            return numOfZero();
        }
        final Num previousCloseValue = previousClose.getValue(index);
        final Num closeValue = close.getValue(index);
        Num previousValue = getValue(index - 1);
        if (closeValue.isGreaterThan(previousCloseValue)) {
            previousValue = previousValue.add(volume.getValue(index));
        } else if (closeValue.isLessThan(previousCloseValue)) {
            previousValue = previousValue.subtract(volume.getValue(index));
        }
        return previousValue;
    }
}
