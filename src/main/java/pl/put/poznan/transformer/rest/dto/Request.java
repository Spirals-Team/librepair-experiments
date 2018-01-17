package pl.put.poznan.transformer.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    @JsonProperty(required = true)
    private String text;
    @JsonProperty(required = true)
    private String[] transforms;

    public Request() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTransforms(String[] transforms) {
        this.transforms = transforms;
    }

    public String getText() {
        return text;
    }

    public String[] getTransforms() {
        return transforms;
    }
}
