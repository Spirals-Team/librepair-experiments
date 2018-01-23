package pl.put.poznan.transformer.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StringResponse {
    @JsonProperty
    private int code;
    @JsonProperty
    private String data;

    public StringResponse(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }
}
