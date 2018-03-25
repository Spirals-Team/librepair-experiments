package me.phlask.api.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class TapResponse {
    private String response;

    @JsonGetter("response")
    public String getResponse() {
        return response;
    }

    @JsonSetter("response")
    public TapResponse setResponse(String response) {
        this.response = response;
        return this;
    }
}
