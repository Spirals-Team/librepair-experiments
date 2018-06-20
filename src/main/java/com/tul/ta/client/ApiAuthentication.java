package com.tul.ta.client;

public interface ApiAuthentication {
    String getAccessToken();

    String getAuthHeader();

    boolean updateAccessToken();
}
