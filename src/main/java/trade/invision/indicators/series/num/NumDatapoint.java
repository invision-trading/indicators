package trade.invision.indicators.series.num;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import trade.invision.num.Num;

import java.time.Instant;

/**
 * {@link NumDatapoint} represents a {@link Num} value at a point in time.
 */
@Value @AllArgsConstructor @Builder(toBuilder = true)
public class NumDatapoint {

    Instant timestamp;
    Num value;
}
