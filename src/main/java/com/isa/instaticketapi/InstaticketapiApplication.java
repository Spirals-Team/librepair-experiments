package com.isa.instaticketapi;

import com.isa.instaticketapi.config.ApplicationConstants;
import com.isa.instaticketapi.config.ApplicationProperties;
import com.isa.instaticketapi.config.DefaultProfileUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@ComponentScan
@EnableAutoConfiguration
@EnableConfigurationProperties(ApplicationProperties.class)
public class InstaticketapiApplication {

    private final Environment environment;

    private static final Logger logger = LoggerFactory.getLogger(InstaticketapiApplication.class);

    public InstaticketapiApplication(Environment environment) {
        this.environment = environment;
    }

    /**
     * Initializes InstaTicketApplication
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        if (activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_PRODUCTION))
            logger.error("It should not run with both 'dev and 'prod' profiles at same time");
    }

    /**
     * Main method, used to run the application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(InstaticketapiApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment environment = app.run(args).getEnvironment();
        String protocol = "http";
        if (environment.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        logger.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}\n\t" +
                        "External: \t{}://{}:{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                environment.getProperty("spring.application.name"),
                protocol,
                environment.getProperty("server.port"),
                protocol,
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"),
                environment.getActiveProfiles());
    }
}
