package dev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import(JpaTestConfig.class)
@EnableJpaRepositories("dev.repositories")
public class DataJpaTestConfig {
  
}
