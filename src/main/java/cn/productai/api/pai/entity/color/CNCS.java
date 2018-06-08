package cn.productai.api.pai.entity.color;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CNCS {

    @JsonProperty("chroma")
    private String chroma;

    @JsonProperty("cncs")
    private String cncs;

    @JsonProperty("color")
    private String color;

    @JsonProperty("lightness")
    private String lightness;

    @JsonProperty("rgb")
    private String[] RGB;

    public String getChroma() {
        return chroma;
    }

    public void setChroma(String chroma) {
        this.chroma = chroma;
    }

    public String getCncs() {
        return cncs;
    }

    public void setCncs(String cncs) {
        this.cncs = cncs;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLightness() {
        return lightness;
    }

    public void setLightness(String lightness) {
        this.lightness = lightness;
    }

    public String[] getRGB() {
        return RGB;
    }

    public void setRGB(String[] RGB) {
        this.RGB = RGB;
    }
}
