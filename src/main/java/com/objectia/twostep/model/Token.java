package com.objectia.twostep.model;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    private final String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("scope")
    private String scope;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("refresh_token")
    private String refreshToken;

    public Token() {
        this.accessToken = null;
        this.tokenType = null;
        this.scope = null;
        this.expiresIn = 0;
        this.refreshToken = null;
    }

    public Token(String access, String typ, String scope, int exp, String refresh) {
        this.accessToken = access;
        this.tokenType = typ;
        this.scope = scope;
        this.expiresIn = exp;
        this.refreshToken = refresh;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getTokenType() {
        return this.accessToken;
    }

    public String getScope() {
        return this.scope;
    }

    public int getExpiresIn() {
        return this.expiresIn;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    /**
     * Check if token is valid.
     * 
     * @return true if token is valid, false otherwise.
     */
    public boolean isValid() {
        if (this.accessToken == null || this.accessToken.isEmpty()) {
            return false;
        }
        if (this.refreshToken == null || this.refreshToken.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
       * Check if access_token has expired.
       * 
       * @return true if token has expired, false otherwise.
       */
    public boolean isExpired() {
        try {
            DecodedJWT jwt = JWT.decode(this.accessToken);

            Date now = new Date();
            Date expiresAt = jwt.getExpiresAt();
            if (now.after(expiresAt)) {
                // Token has expired
                return false;
            }
        } catch (JWTDecodeException exception) {
            // Invalid token
            return false;
        }

        return true;
    }
}