package trade.invision.indicators.indicators.mf.directional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import trade.invision.num.Num;

/**
 * {@link DirectionalMoneyFlowResult} contains the results for {@link DirectionalMoneyFlow}.
 */
@Value @AllArgsConstructor @Builder(toBuilder = true)
public class DirectionalMoneyFlowResult {

    /**
     * The negative money flow.
     */
    Num negative;
    /**
     * The positive money flow.
     */
    Num positive;
}
