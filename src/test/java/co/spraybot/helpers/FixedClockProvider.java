package co.spraybot.helpers;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

public interface FixedClockProvider {

    default Clock fixedClock() {
        ZoneId zoneId = ZoneId.systemDefault();
        return Clock.fixed(referenceTime().atZone(zoneId).toInstant(), zoneId);
    }

    default LocalDateTime referenceTime() {
        return LocalDateTime.of(2015, 3, 2, 9, 0);
    }
}
