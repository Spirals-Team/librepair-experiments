package guru.bonacci.spectre.spectreshared.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SpectreEventChannels {

	String ENRICHMENT = "enrichment";

    @Input(ENRICHMENT)
    SubscribableChannel enrichmentChannel();
}
