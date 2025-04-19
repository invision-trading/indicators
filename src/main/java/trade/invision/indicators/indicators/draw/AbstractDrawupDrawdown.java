package trade.invision.indicators.indicators.draw;

import org.jetbrains.annotations.Nullable;
import trade.invision.indicators.indicators.Indicator;
import trade.invision.indicators.indicators.extrema.global.GlobalMaximum;
import trade.invision.indicators.indicators.extrema.global.GlobalMinimum;
import trade.invision.indicators.indicators.extrema.local.LocalMaximum;
import trade.invision.indicators.indicators.extrema.local.LocalMinimum;
import trade.invision.num.Num;

/**
 * {@link AbstractDrawupDrawdown} is an abstract {@link Num} {@link Indicator} for local/global drawup/drawdown
 * percentage/difference {@link Indicator}s.
 */
public abstract class AbstractDrawupDrawdown extends Indicator<Num> {

    private final Indicator<Num> indicator;
    private final boolean drawup;
    private final boolean percentage;
    private final Indicator<Num> extremaIndicator;

    /**
     * Instantiates a new {@link AbstractDrawupDrawdown}.
     *
     * @param indicator  the {@link Indicator}
     * @param length     the number of values to look back at, or <code>null</code> for global drawup/drawdown
     * @param drawup     <code>true</code> for drawup, <code>false</code> for drawdown
     * @param percentage <code>true</code> for percentage, <code>false</code> for difference
     */
    public AbstractDrawupDrawdown(Indicator<Num> indicator, @Nullable Integer length,
            boolean drawup, boolean percentage) {
        super(indicator.getSeries(), 0);
        this.indicator = indicator;
        this.drawup = drawup;
        this.percentage = percentage;
        if (length == null) {
            extremaIndicator = drawup ? new GlobalMinimum(indicator) : new GlobalMaximum(indicator);
        } else {
            extremaIndicator = drawup ? new LocalMinimum(indicator, length) : new LocalMaximum(indicator, length);
        }
    }

    @Override
    protected Num calculate(long index) {
        final Num extremaValue = extremaIndicator.getValue(index);
        final Num currentValue = indicator.getValue(index);
        final Num value = percentage ? currentValue.divide(extremaValue).decrement() :
                currentValue.subtract(extremaValue);
        return drawup ? numOfZero().maximum(value) : numOfZero().minimum(value);
    }
}
