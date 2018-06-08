package cn.productai.api.pai.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class DressColor {
    @JsonProperty("basic-cn")
    private String cnName;

    @JsonProperty("rgb")
    private byte[] rgb;

    @JsonProperty("w3c-cn")
    private String w3cCn;

    @JsonProperty("w3c-en")
    private String w3cEn;

    @JsonProperty("basic-en")
    private String enName;

    @JsonProperty("ncs")
    private String ncs;

    @JsonProperty("percent")
    private double percent;

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public byte[] getRgb() {
        return rgb;
    }

    public void setRgb(byte[] rgb) {
        this.rgb = rgb;
    }

    public String getW3cCn() {
        return w3cCn;
    }

    public void setW3cCn(String w3cCn) {
        this.w3cCn = w3cCn;
    }

    public String getW3cEn() {
        return w3cEn;
    }

    public void setW3cEn(String w3cEn) {
        this.w3cEn = w3cEn;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getNcs() {
        return ncs;
    }

    public void setNcs(String ncs) {
        this.ncs = ncs;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
