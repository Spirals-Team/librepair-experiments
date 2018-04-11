package guru.bonacci.oogway.entrance.events;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EntranceEventChannels {

    String SPECTRE = "spectre";

    @Output(SPECTRE)
    MessageChannel spectreChannel();
}
