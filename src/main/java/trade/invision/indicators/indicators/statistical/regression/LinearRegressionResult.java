package trade.invision.indicators.indicators.statistical.regression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import trade.invision.num.Num;

@Value @AllArgsConstructor @Builder(toBuilder = true)
public class LinearRegressionResult {

    /**
     * The slope value.
     */
    Num slope;
    /**
     * The intercept value.
     */
    Num intercept;
    /**
     * The Y value (at the <code>index</code>).
     */
    Num y;
    /**
     * The next Y value (at <code>index + 1</code>).
     */
    Num nextY;
    /**
     * Residual sum of squares (RSS).
     */
    Num rss;
    /**
     * Total sum of squares (TSS).
     */
    Num tss;
    /**
     * Coefficient of determination (R^2).
     */
    Num r2;
}
