package com.prussia.play.spring.web.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import groovy.util.logging.Slf4j;

@EnableWebSecurity
@Slf4j
@Order(SecurityProperties.BASIC_AUTH_ORDER-1)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private SecurityProperties security;
	@Autowired 
	private Environment environment;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests().antMatchers("/**").permitAll()
//		.anyRequest().authenticated()
//		.and().formLogin()
//		.loginPage("/login.jsp").permitAll()
		.and()
		.sessionManagement().sessionCreationPolicy(this.security.getSessions())
		.sessionFixation()
		.none().maximumSessions(1)
		.and().invalidSessionUrl("/api/login/invalid")
//		.changeSessionId()
//		.migrateSession()
		;
		boolean isSwagger = false;
		isSwagger = checkProfile4Swagger(isSwagger);
		if (!isSwagger) {
			http.addFilterBefore(new SessionFilter(), ChannelProcessingFilter.class);
		}

		http.addFilterAfter(new SessionFilter(), ChannelProcessingFilter.class);
	}

	private boolean checkProfile4Swagger(boolean isSwagger) {
		for(String profile : environment.getActiveProfiles()){
			if("dev".equalsIgnoreCase(profile)){
				isSwagger = true;
			}
        }
		return isSwagger;
	}

}
