package trade.invision.indicators.indicator.bb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import trade.invision.num.Num;

/**
 * {@link BollingerBandsResult} contains the results for {@link BollingerBands}.
 */
@Value @AllArgsConstructor @Builder(toBuilder = true)
public class BollingerBandsResult {

    /**
     * The middle band of the Bollinger Bands.
     */
    Num middleBand;
    /**
     * The upper band of the Bollinger Bands.
     */
    Num upperBand;
    /**
     * The lower band of the Bollinger Bands.
     */
    Num lowerBand;
    /**
     * The bandwidth of the Bollinger Bands.
     *
     * @see <a href="https://www.tradingview.com/support/solutions/43000501972-bollinger-bandwidth-bbw/">TradingView</a>
     */
    Num bandwidth;
    /**
     * The %b of the Bollinger Bands.
     *
     * @see <a href="https://www.tradingview.com/support/solutions/43000501971-bollinger-bands-b-b/">TradingView</a>
     */
    Num percentB;
}
