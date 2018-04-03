package com.raouf.backend.security;

import com.raouf.backend.common.RaoufApiEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @see <a href="http://www.keycloak.org/docs/latest/securing_apps/topics/oidc/java/spring-security-adapter.html">Keycloak doc</a>
 * Inspired by <a href="https://developers.redhat.com/blog/2017/05/25/easily-secure-your-spring-boot-applications-with-keycloak/">This article</a>
 */
@EnableWebSecurity
public class SwaggerSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SWAGGER_ROLE = "SWAGGER";

    @Value("${security.swagger.user.login}")
    private String swaggerUserLogin;

    @Value("${security.swagger.user.pwd}")
    private String swaggerUserPwd;

    private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder>
    inMemoryConfigurer() {
        return new InMemoryUserDetailsManagerConfigurer<>();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        inMemoryConfigurer().withUser(swaggerUserLogin).password(swaggerUserPwd).roles(SWAGGER_ROLE)
                            .and()
                            .configure(auth);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .requestMatchers()
                .antMatchers(RaoufApiEndpoints.ROOT,
                             RaoufApiEndpoints.SWAGGER_UI,
                             RaoufApiEndpoints.SWAGGER,
                             RaoufApiEndpoints.SWAGGER_RESOURCES,
                             RaoufApiEndpoints.SWAGGER_API_DOCS,
                             RaoufApiEndpoints.SWAGGER_WEBJARS)
                .and()
                .authorizeRequests()
                .antMatchers(RaoufApiEndpoints.SWAGGER_RESOURCES).permitAll()
                .antMatchers(RaoufApiEndpoints.ROOT,
                             RaoufApiEndpoints.SWAGGER_UI,
                             RaoufApiEndpoints.SWAGGER,
                             RaoufApiEndpoints.SWAGGER_WEBJARS,
                             RaoufApiEndpoints.SWAGGER_API_DOCS).hasRole(SWAGGER_ROLE)
                .and()
                .httpBasic();
    }
}
