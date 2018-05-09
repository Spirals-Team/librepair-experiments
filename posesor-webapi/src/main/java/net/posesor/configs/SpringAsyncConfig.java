package net.posesor.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Enables async support in Spring
 * <p>
 * Read more @ http://www.baeldung.com/spring-async
 */
@Configuration
@EnableAsync
public class SpringAsyncConfig {
}
