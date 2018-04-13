package guru.bonacci.oogway.oracle.service.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OracleEventChannels {

    String ORACLE = "oracle";

    @Output(ORACLE)
    MessageChannel oracleChannel();
    
    
	String SANNYAS = "sannyas";

    @Input(SANNYAS)
    SubscribableChannel sannyasChannel();

}
