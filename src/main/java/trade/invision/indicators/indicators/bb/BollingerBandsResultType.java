package trade.invision.indicators.indicators.bb;

/**
 * {@link BollingerBandsResultType} defines {@link BollingerBands} result types.
 */
public enum BollingerBandsResultType {

    /**
     * The middle band of the Bollinger Bands.
     */
    MIDDLE_BAND,

    /**
     * The upper band of the Bollinger Bands.
     */
    UPPER_BAND,

    /**
     * The lower band of the Bollinger Bands.
     */
    LOWER_BAND,

    /**
     * The bandwidth of the Bollinger Bands.
     *
     * @see <a href="https://www.tradingview.com/support/solutions/43000501972-bollinger-bandwidth-bbw/">TradingView</a>
     */
    BANDWIDTH,

    /**
     * The %b of the Bollinger Bands.
     *
     * @see <a href="https://www.tradingview.com/support/solutions/43000501971-bollinger-bands-b-b/">TradingView</a>
     */
    PERCENT_B
}
