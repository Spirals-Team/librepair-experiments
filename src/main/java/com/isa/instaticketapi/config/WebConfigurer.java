package com.isa.instaticketapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.*;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import io.undertow.UndertowOptions;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import javax.servlet.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.Set;


/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, EmbeddedServletContainerCustomizer {

    private final Logger logger = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment environment;

    private final ApplicationProperties applicationProperties;

    public WebConfigurer(Environment environment, ApplicationProperties applicationProperties) {
        this.environment = environment;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (environment.getActiveProfiles().length != 0)
            logger.info("Web application cofiguration, using profile: {}", (Object[]) environment.getActiveProfiles());

        logger.info("Web application fully configured");

    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("html", MediaType.TEXT_HTML_VALUE + ";charset=utf-8");
        mappings.add("json", MediaType.TEXT_HTML_VALUE + ";charset=utf-8");
        container.setMimeMappings(mappings);
        setLocationForStaticAssets(container);

        if (applicationProperties.getHttp().getVersion().equals(ApplicationProperties.Http.Version.V_2_0) &&
                container instanceof UndertowEmbeddedServletContainerFactory) {

            ((UndertowEmbeddedServletContainerFactory) container)
                    .addBuilderCustomizers(builder ->
                            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true));
        }
    }

    private void setLocationForStaticAssets(ConfigurableEmbeddedServletContainer container) {
        File root;
        String prefixPath = resolvePathPrefix();
        root = new File(prefixPath + "target/www/");
        if (root.exists() && root.isDirectory()) {
            container.setDocumentRoot(root);
        }
    }

    /**
     * Resolve path prefix to static resources.
     */
    private String resolvePathPrefix() {
        String fullExecutablePath = this.getClass().getResource("").getPath();
        String rootPath = Paths.get(".").toUri().normalize().getPath();
        String extractedPath = fullExecutablePath.replace(rootPath, "");
        int extractionEndIndex = extractedPath.indexOf("target/");
        if (extractionEndIndex <= 0) {
            return "";
        }
        return extractedPath.substring(0, extractionEndIndex);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = applicationProperties.getCors();
        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
            logger.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
        }
        return new CorsFilter(source);
    }
}
