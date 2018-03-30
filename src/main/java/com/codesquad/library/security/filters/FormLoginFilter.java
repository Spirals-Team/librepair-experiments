package com.codesquad.library.security.filters;

import com.codesquad.library.domain.authentication.ActiveUser;
import com.codesquad.library.domain.exceptions.ErrorCodes;
import com.codesquad.library.domain.exceptions.security.InternalSecurityProcessingException;
import com.codesquad.library.dtos.security.FormLoginDocument;
import com.codesquad.library.security.LoginAccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(FormLoginFilter.class);

    private AuthenticationManager authenticationManager;
    private JwtAccessTokenConverter converter;

    public FormLoginFilter(String defaultFilterProcessingUrl, AuthenticationManager authenticationManager, JwtAccessTokenConverter converter) {
        super(defaultFilterProcessingUrl);
        this.authenticationManager = authenticationManager;
        this.converter = converter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        FormLoginDocument document = null;

        try {
            document = new ObjectMapper().readValue(httpServletRequest.getInputStream(), FormLoginDocument.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throwSecurityException();
        }

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(document, document.getPassword(), null));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) {
        OAuth2Authentication authentication = (OAuth2Authentication) auth;

        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            processAuthRequest(res, authentication);
        } catch(JsonProcessingException e) {
            throwSecurityException();
        }
    }

    private void processAuthRequest(HttpServletResponse res, OAuth2Authentication auth) throws JsonProcessingException {

        OAuth2AccessToken token = new LoginAccessToken((ActiveUser)auth.getPrincipal());
        String tokenString = new ObjectMapper().writeValueAsString(this.converter.enhance(token, auth));

        writeResponse(res, tokenString);
    }

    private void writeResponse(HttpServletResponse res, String content) throws InternalSecurityProcessingException {
        res.setStatus(HttpStatus.OK.value());
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        try {
            res.getWriter().write(content);
        } catch (IOException e) {
            throwSecurityException();
        }
    }

    private void throwSecurityException() throws InternalSecurityProcessingException {
        throw new InternalSecurityProcessingException(ErrorCodes.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, "로그인중 서버 오류가 발생하였습니다.");
    }
}
