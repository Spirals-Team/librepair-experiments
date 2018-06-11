package by.imsha;

import by.imsha.auth0.Auth0Client;
import com.auth0.spring.security.api.Auth0SecurityConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * @author Alena Misan
 */

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends Auth0SecurityConfig {


    @Value("${spring.profiles.active}")
    private String env;

    @Value("${auth.security.debug}")
    private boolean securityDebug ;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(securityDebug);
    }

    /**
     * Provides Auth0 API access
     */
    @Bean
    public Auth0Client auth0Client() {

        return new Auth0Client(clientId, issuer);
    }

    /**
     *  Our API Configuration - for Profile CRUD operations
     *
     *  Here we choose not to bother using the `auth0.securedRoute` property configuration
     *  and instead ensure any unlisted endpoint in our config is secured by default
     */
    @Override
    protected void authorizeRequests(final HttpSecurity http) throws Exception {
        // include some Spring Boot Actuator endpoints to check metrics
        // add others or remove as you choose, this is just a sample config to illustrate
        // most specific rules must come - order is important (see Spring Security docs)
//        if(env.equals("production")){
        //TODO fix secured authorization
//            http.authorizeRequests().antMatchers(securedRoute).authenticated().antMatchers("/**").permitAll();
//        }

    }

}
