package trade.invision.indicators.series.bar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.jetbrains.annotations.Nullable;
import trade.invision.num.Num;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;

import static java.time.Duration.between;

/**
 * {@link Bar} represents the price data of an instrument within a span of time. A {@link Bar} may also be referred to
 * as a "candlestick" or "OHLC" (Open, High, Low, Close).
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
@Value @AllArgsConstructor @Builder(toBuilder = true)
public class Bar {

    /**
     * The start {@link Instant} of this {@link Bar} (inclusive).
     */
    Instant start;
    /**
     * The end {@link Instant} of this {@link Bar} (exclusive).
     */
    Instant end;
    Num open;
    Num high;
    Num low;
    Num close;
    Num volume;
    Num tradeCount;

    /**
     * Instantiates a new {@link Bar}.
     *
     * @param start       the start {@link Instant}
     * @param duration    the {@link Duration}
     * @param tradePrice  the trade price {@link Num}
     * @param tradeVolume the trade volume {@link Num}
     */
    public Bar(Instant start, Duration duration, Num tradePrice, Num tradeVolume) {
        this(start, duration, tradePrice, tradePrice, tradePrice, tradePrice, tradeVolume, tradePrice.factory().one());
    }

    /**
     * Instantiates a new {@link Bar}.
     *
     * @param start      the start {@link Instant}
     * @param duration   the {@link Duration}
     * @param open       the open {@link Num}
     * @param high       the high {@link Num}
     * @param low        the low {@link Num}
     * @param close      the close {@link Num}
     * @param volume     the volume {@link Num}
     * @param tradeCount the trade count {@link Num}
     */
    public Bar(Instant start, Duration duration, Num open, Num high, Num low, Num close, Num volume, Num tradeCount) {
        this.start = start;
        end = start.plus(duration);
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.tradeCount = tradeCount;
    }

    /**
     * Gets the {@link Duration} (period) of this {@link Bar}.
     *
     * @return the {@link Duration}
     *
     * @see Duration#between(Temporal, Temporal)
     * @see #getStart()
     * @see #getEnd()
     */
    public Duration getDuration() {
        return between(start, end);
    }

    /**
     * Calls {@link #addTrade(Num, Num)} with <code>volume</code> set to <code>null</code>.
     */
    public Bar addPrice(Num price) {
        return addTrade(price, null);
    }

    /**
     * Creates a new {@link Bar} from this {@link Bar} with the new trade data.
     *
     * @param price  the price {@link Num}
     * @param volume the volume {@link Num}, or <code>null</code> to not update {@link #getVolume()}
     *
     * @return the new {@link Bar}
     */
    public Bar addTrade(Num price, @Nullable Num volume) {
        return new Bar(start, end,
                open, high.maximum(price), low.minimum(price), price,
                volume == null ? this.volume : this.volume.add(volume), tradeCount.increment());
    }

    /**
     * Creates a new {@link Bar} by aggregating this {@link Bar} and the given {@link Bar}.
     *
     * @param bar the {@link Bar}
     *
     * @return the new {@link Bar}
     */
    public Bar aggregate(Bar bar) {
        final boolean thisStart = !start.isAfter(bar.start);
        final boolean otherEnd = !bar.end.isBefore(end);
        return new Bar(thisStart ? start : bar.start, otherEnd ? bar.end : end,
                thisStart ? open : bar.open, high.maximum(bar.high), low.minimum(bar.low), otherEnd ? bar.close : close,
                volume.add(bar.volume), tradeCount.add(bar.tradeCount));
    }

    /**
     * Checks if the given {@link Instant} is contained within {@link #getStart()} (inclusive) and {@link #getEnd()}
     * (exclusive).
     *
     * @param instant the {@link Instant}
     *
     * @return <code>true</code> if contained, <code>false</code> otherwise
     */
    public boolean containsInstant(Instant instant) {
        return !instant.isBefore(start) && instant.isBefore(end);
    }

    /**
     * Checks if the given {@link Bar} {@link #getStart()} (inclusive) is {@link #containsInstant(Instant)} of this
     * {@link Bar} or if the given {@link Bar} {@link #getEnd()} (exclusive) is {@link #containsInstant(Instant)} of
     * this {@link Bar}.
     *
     * @param bar a {@link Bar}
     *
     * @return <code>true</code> if overlaps, <code>false</code> otherwise
     */
    public boolean overlaps(Bar bar) {
        return containsInstant(bar.start) || containsInstant(bar.end);
    }

    /**
     * Checks if {@link #getClose()} is greater than {@link #getOpen()}.
     *
     * @return <code>true</code> if bullish, <code>false</code> otherwise
     */
    public boolean isBullish() {
        return close.isGreaterThan(open);
    }

    /**
     * Checks if {@link #getClose()} is less than {@link #getOpen()}.
     *
     * @return <code>true</code> if bearish, <code>false</code> otherwise
     */
    public boolean isBearish() {
        return close.isLessThan(open);
    }
}
