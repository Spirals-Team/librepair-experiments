package com.objectia;

import com.objectia.twostep.model.TokenStore;

public class Twostep {

    public static final String VERSION = "1.0.0";
    public static final String DEFAULT_API_BASE = "https://api.twostep.io/v1";
    public static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 80 * 1000;

    public static volatile String userAgent = "twostep-go/" + VERSION;
    public static volatile String apiBase = DEFAULT_API_BASE;
    public static volatile int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    public static volatile int readTimeout = DEFAULT_READ_TIMEOUT;

    public static volatile String clientId;
    public static volatile String clientSecret;

    public static volatile TokenStore tokenStore = null;

    public static void setTokenStore(TokenStore ts) {
        tokenStore = ts;
    }

    public static TokenStore getTokenStore() {
        return tokenStore;
    }
}

