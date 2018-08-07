package guru.bonacci.oogway.sannyas.service.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SannyasEventChannels {

    String SANNYAS = "sannyas";

    @Output(SANNYAS)
    MessageChannel sannyasChannel();
    
    
	String ORACLE = "oracle";

    @Input(ORACLE)
    SubscribableChannel oracleChannel();

}
