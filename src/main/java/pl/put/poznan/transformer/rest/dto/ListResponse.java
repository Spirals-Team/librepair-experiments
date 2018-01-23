package pl.put.poznan.transformer.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListResponse {
    @JsonProperty
    private int code;
    @JsonProperty
    private List<String> data;

    public ListResponse(int code, List<String> data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public List<String> getData() {
        return data;
    }
}
