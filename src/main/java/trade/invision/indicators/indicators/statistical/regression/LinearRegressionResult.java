package trade.invision.indicators.indicators.statistical.regression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import trade.invision.num.Num;

/**
 * {@link LinearRegressionResult} contains the results for {@link LinearRegression}.
 */
@Value @AllArgsConstructor @Builder(toBuilder = true)
public class LinearRegressionResult {

    /**
     * The slope.
     */
    Num slope;
    /**
     * The <i>y</i>-intercept.
     */
    Num intercept;
    /**
     * The <i>y</i> value at <code>index</code>.
     */
    Num y;
    /**
     * The <i>y</i> value at <code>index + 1</code>.
     */
    Num nextY;
    /**
     * The residual sum of squares (RSS).
     */
    Num rss;
    /**
     * The total sum of squares (TSS).
     */
    Num tss;
    /**
     * The coefficient of determination (R^2).
     */
    Num r2;
}
