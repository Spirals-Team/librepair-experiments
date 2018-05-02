package net.posesor;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseDocumentCommandHandlerConfiguration {

    @Autowired
    AxonConfiguration axonConfiguration;

    @Bean
    ExpenseDocumentCommandHandler expenseDocumentCommandHandler() {
        return new ExpenseDocumentCommandHandler(axonConfiguration.repository(ExpenseDocument.class));
    }
}
