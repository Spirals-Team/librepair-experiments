package net.posesor.configs;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(new DemoUsersAuthenticationProvider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(new CorsConfigurer())
                .and()
                .authorizeRequests().antMatchers("/api/**").access("hasRole('USER')")
                .and()
                .httpBasic();
    }

    /**
     * Application allows to sign-on any user with name starting with 'demo* and with
     * password same as username.
     * It is used to simply allows start using application.
     */
    static class DemoUsersAuthenticationProvider implements AuthenticationProvider {

        @Override
        public Authentication authenticate(Authentication authentication) {
            UsernamePasswordAuthenticationToken actual = (UsernamePasswordAuthenticationToken) authentication;
            val userName = (String) actual.getPrincipal();
            if (!userName.startsWith("demo")) return null;
            val userPassword = (String) actual.getCredentials();
            if (!userPassword.equals(userName))
                throw new BadCredentialsException("password for demo user need to be same as username.");

            return new UsernamePasswordAuthenticationToken(
                    actual.getPrincipal(), actual.getCredentials(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return authentication == UsernamePasswordAuthenticationToken.class;
        }
    }
}
