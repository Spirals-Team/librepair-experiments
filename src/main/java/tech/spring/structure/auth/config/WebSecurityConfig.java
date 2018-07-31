package tech.spring.structure.auth.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Arrays;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import tech.spring.structure.auth.handler.StructureAccessDeniedExceptionHandler;
import tech.spring.structure.auth.handler.StructureAuthenticationEntryPoint;
import tech.spring.structure.auth.handler.StructureAuthenticationFailureHandler;
import tech.spring.structure.auth.handler.StructureAuthenticationSuccessHandler;
import tech.spring.structure.auth.handler.StructureLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${facade.url:http://localhost:4200}")
    private String facadeUrl;

    @Value("${structure.whitelist:}")
    private String[] whitelist;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityExpressionHandler<FilterInvocation> securityExpressionHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList(facadeUrl));
        configuration.setAllowedMethods(Arrays.asList("GET", "DELETE", "PUT", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Origin", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**/*", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        // NOTE: anonymous access
        http
            .authorizeRequests()
                .antMatchers(GET, "/menu", "/scaffold", "/scaffolding")
                    .permitAll();
        // NOTE: authorize all mappings if whitelisted
        if(hasWhitelist()) {
            http
                .authorizeRequests()
                    .antMatchers("/**")
                        .access(buildAccessExpression());
        }
        // NOTE: authenticated authorizations
        http
            .authorizeRequests()
                .expressionHandler(securityExpressionHandler)
                .antMatchers(POST, "/users")
                    .denyAll()
                .antMatchers(PUT, "/users/{id}")
                    .denyAll()
                .antMatchers("/users", "/users/{id}")
                    .hasRole("ADMIN")
                .anyRequest()
                    .authenticated()
            .and()
                .formLogin()
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler())
                        .permitAll()
            .and()
                .logout()
                    .deleteCookies("remove")
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler(logoutSuccessHandler())
                        .permitAll()
            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(accessDeniedHandler())
            .and()
                .requestCache()
                    .requestCache(nullRequestCache())
            .and()
                .cors()
            .and()
                .csrf()
                    .disable();
       // @formatter:on
    }

    private boolean hasWhitelist() {
        return whitelist.length > 0;
    }

    private String buildAccessExpression() {
        StringBuilder accessExpression = new StringBuilder();
        Iterator<String> whitelistIterator = Arrays.asList(whitelist).iterator();
        while (whitelistIterator.hasNext()) {
            accessExpression.append("hasIpAddress('" + whitelistIterator.next() + "')");
            if (whitelistIterator.hasNext()) {
                accessExpression.append(" or ");
            }
        }
        return accessExpression.toString();
    }

    private StructureAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new StructureAuthenticationSuccessHandler(objectMapper);
    }

    private StructureAuthenticationFailureHandler authenticationFailureHandler() {
        return new StructureAuthenticationFailureHandler();
    }

    private StructureLogoutSuccessHandler logoutSuccessHandler() {
        return new StructureLogoutSuccessHandler();
    }

    private StructureAuthenticationEntryPoint authenticationEntryPoint() {
        return new StructureAuthenticationEntryPoint();
    }

    private StructureAccessDeniedExceptionHandler accessDeniedHandler() {
        return new StructureAccessDeniedExceptionHandler();
    }

    private NullRequestCache nullRequestCache() {
        return new NullRequestCache();
    }

}
