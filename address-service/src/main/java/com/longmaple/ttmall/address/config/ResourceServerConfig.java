package com.longmaple.ttmall.address.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.DelegatingJwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.IssuerClaimVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter implements JwtAccessTokenConverterConfigurer {

	@Autowired    
	private UserDetailsService userDetailsService;   

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.requestMatchers().antMatchers("/address")
		.and().cors();
	} 

	private DefaultAccessTokenConverter tokenConverter() {        
		DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();        
		userAuthenticationConverter.setUserDetailsService(userDetailsService);     
		DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
		tokenConverter.setUserTokenConverter(userAuthenticationConverter); 
		return tokenConverter;    
	}

	@Override
	public void configure(JwtAccessTokenConverter converter) {
		converter.setJwtClaimsSetVerifier(jwtClaimsSetVerifier());
		converter.setAccessTokenConverter(tokenConverter());
	}
	
	private JwtClaimsSetVerifier jwtClaimsSetVerifier() {
	    return new DelegatingJwtClaimsSetVerifier(Arrays.asList(
	      issuerClaimVerifier(), customJwtClaimVerifier()));
	}

	private JwtClaimsSetVerifier issuerClaimVerifier() {
		try {
			return new IssuerClaimVerifier(new URL("http://localhost:8080"));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}   
	
	private JwtClaimsSetVerifier customJwtClaimVerifier() {
	    return new CustomClaimVerifier();
	}
	
	private class CustomClaimVerifier implements JwtClaimsSetVerifier {
	    @Override
	    public void verify(Map<String, Object> claims) throws InvalidTokenException {
	        String username = (String) claims.get("user_name");
	        if ((username == null) || (username.length() == 0)) {
	            throw new InvalidTokenException("invalid user_name claim in JWT");
	        }
	    }
	}
}
