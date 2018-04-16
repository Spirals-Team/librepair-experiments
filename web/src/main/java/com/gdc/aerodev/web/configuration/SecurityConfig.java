package com.gdc.aerodev.web.configuration;

import com.gdc.aerodev.model.User;
import com.gdc.aerodev.service.impl.UserService;
import com.gdc.aerodev.service.security.Hasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                    .antMatchers("/home").permitAll()
                    .antMatchers("/profile", "/user/**", "create_prj", "/project/**").hasRole("USER")
                    .and()
                .formLogin()
                    .usernameParameter("name")
                    .loginPage("/login").
                successHandler(new MySuccessHandler(userService))
                    .and()
                .csrf()
                    .disable()
                .httpBasic();
        // @formatter:on
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.getUser(username);
            if (user == null) {
                throw new UsernameNotFoundException("User '" + username + "' was not found in the database");
            }
            return UserWrapper.wrap(user);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return Hasher.hash(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return Hasher.hash(rawPassword.toString()).equals(encodedPassword);
            }
        };
    }

    static class UserWrapper implements UserDetails {
        private final User user;

        private UserWrapper(User user) {
            this.user = user;
        }

        public static UserWrapper wrap(User user) {
            return new UserWrapper(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            // FIXME: stub, add roles to the database
            return Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_USER")
//                    new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")
            );
        }

        public Long getId(){return user.getUserId();}

        @Override
        public String getPassword() {
            return user.getUserPassword();
        }

        @Override
        public String getUsername() {
            return user.getUserName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
