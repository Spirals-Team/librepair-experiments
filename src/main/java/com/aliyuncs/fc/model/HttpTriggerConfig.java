package com.aliyuncs.fc.model;

import com.google.gson.annotations.SerializedName;

public class HttpTriggerConfig {

    @SerializedName("authType")
    private String authType;

    @SerializedName("methods")
    private String[] methods;

    public HttpTriggerConfig(String authType, String[] methods) {
        this.authType = authType;
        this.methods = methods;
    }

    public String getAuthType() {
        return authType;
    }

    public String[] getMethods() {
        return methods;
    }
}
