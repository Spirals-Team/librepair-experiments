package net.posesor.configs;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

/**
 * I don't see any added value to have CORS limitations in my project.
 */
public final class CorsConfigurer implements CorsConfigurationSource {

    private final CorsConfiguration configuration = new CorsConfiguration();

    CorsConfigurer() {
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
    }

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        return configuration;
    }
}
