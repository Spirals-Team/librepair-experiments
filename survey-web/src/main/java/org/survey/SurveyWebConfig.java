package org.survey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import({
        ServiceConfig.class,
        SecurityConfig.class
})
public class SurveyWebConfig {
    @Bean
    public DatabaseInitializer getDatabaseInitializer() {
        return new DatabaseInitializer();
    }
}
