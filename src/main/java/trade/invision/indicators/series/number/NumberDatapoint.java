package trade.invision.indicators.series.number;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import trade.invision.num.Num;

import java.time.Instant;

/**
 * {@link NumberDatapoint} represents a numeric value at a point in time.
 */
@Value @AllArgsConstructor @Builder(toBuilder = true)
public class NumberDatapoint {

    Instant timestamp;
    Num value;
}
