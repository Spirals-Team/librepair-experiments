package net.posesor;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChargeDocumentCommandHandlerConfiguration {

    @Autowired
    AxonConfiguration axonConfiguration;

    @Bean
    public ChargeDocumentCommandHandler chargeDocumentCommandHandler() {
        return new ChargeDocumentCommandHandler(axonConfiguration.repository(UnallocatedChargeDocument.class));
    }
}
