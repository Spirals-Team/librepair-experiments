package com.codesquad.library.security;

import com.codesquad.library.domain.authentication.ActiveUser;
import com.codesquad.library.domain.authentication.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class LoginAccessToken implements OAuth2AccessToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int ttl;

    @JsonProperty("scope")
    private Set<String> scopes = Sets.newHashSet();

    @JsonProperty("jti")
    private String value;

    private Map<String, Object> additionalInformation = Maps.newHashMap();
    private OAuth2RefreshToken refreshToken;

    @JsonIgnore
    private long issuedDate;

    public LoginAccessToken(ActiveUser user) {
        Arrays.asList("read", "write").stream().forEach(s -> this.scopes.add(s));

        Member member = user.getMember();

        this.tokenType = "bearer";
        this.ttl = 43200000;
        this.value = String.valueOf(member.getUserId());

        this.additionalInformation.put("USER_LOGINID", member.getLoginEmail());
        this.additionalInformation.put("USER_UID", member.getUserId());
        this.additionalInformation.put("AUTHORITIES", member.getRoles());

        this.issuedDate = System.currentTimeMillis();
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }

    @Override
    public Set<String> getScope() {
        return this.scopes;
    }

    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return this.refreshToken;
    }

    @Override
    public String getTokenType() {
        return this.tokenType;
    }

    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() - this.issuedDate > this.ttl;
    }

    @Override
    public Date getExpiration() {
        return new Date(this.issuedDate + this.ttl);
    }

    @Override
    public int getExpiresIn() {
        return this.ttl;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
