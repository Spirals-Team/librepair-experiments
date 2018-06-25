package com.hashmapinc.haf.configs;

import com.hashmapinc.haf.services.PropertiesClientUserDetailsService;
import com.hashmapinc.haf.tokens.IssuedAtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter{

    @Autowired private JwtSettings settings;

    @Autowired private ClientConfig config;

    @Autowired private UserAuthenticationConverter userDetailsConverter;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        DefaultTokenServices tokenServices = tokenService();
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain())
                .tokenServices(tokenServices);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //TODO: Inject a client details service which uses DB to identify clients information
        clients.withClientDetails(new PropertiesClientUserDetailsService(config));
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(userDetailsConverter);
        converter.setAccessTokenConverter(accessTokenConverter);
        converter.setSigningKey(settings.getTokenSigningKey());
        return converter;
    }

    @Bean
    public TokenEnhancer tokenEnhancerChain(){
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(new IssuedAtTokenEnhancer(), accessTokenConverter()));
        return chain;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenService(){
        DefaultTokenServices service = new DefaultTokenServices();
        service.setTokenStore(tokenStore());
        service.setTokenEnhancer(tokenEnhancerChain());
        service.setSupportRefreshToken(true);
        service.setAuthenticationManager(authenticationManager);
        service.setRefreshTokenValiditySeconds(settings.getRefreshTokenExpTime());
        service.setAccessTokenValiditySeconds(settings.getTokenExpirationTime());
        return service;
    }
}
