package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Configuration
@Profile({ "!" + SpringApplicationProfile.NO_MAIL })
@PropertySource({ "classpath:configuration/mail.properties" })
public class SpringMailConfiguration {
}