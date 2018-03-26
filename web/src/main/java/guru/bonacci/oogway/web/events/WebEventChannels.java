package guru.bonacci.oogway.web.events;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface WebEventChannels {

    String SPECTRE = "spectre";

    @Output(SPECTRE)
    MessageChannel spectreChannel();
}
