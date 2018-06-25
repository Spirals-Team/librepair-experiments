package com.hashmap.haf.functions.api.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.{EnableResourceServer, ResourceServerConfigurerAdapter}

@EnableResourceServer
@Configuration
class FunctionsResourceServer @Autowired()(sso: ResourceServerProperties) extends ResourceServerConfigurerAdapter{

	@throws[Exception]
	override def configure(http: HttpSecurity): Unit = {
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests
			.antMatchers("/", "/demo").permitAll
			.anyRequest.authenticated
	}
}
