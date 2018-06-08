package cn.productai.api.pai.entity.color;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColorAnalysisResult {

    /**
     * cncs相关结果信息
     */
    @JsonProperty("cncs")
    private CNCS cncs;

    /**
     * 基础颜色结果信息
     */
    @JsonProperty("basic")
    private String basic;

    /**
     * w3c标准的结果信息
     */
    @JsonProperty("w3c")
    private String w3c;

    /**
     * ncs相关结果信息
     */
    @JsonProperty("ncs")
    private String ncs;

    /**
     * 颜色比例
     */
    @JsonProperty("freq")
    private String percent;

    /**
     * 16进制颜色码
     */
    @JsonProperty("hex")
    private String hex;

    /**
     * RGB颜色码
     */
    @JsonProperty("rgb")
    private Integer[] rgb;

    public CNCS getCncs() {
        return cncs;
    }

    public void setCncs(CNCS cncs) {
        this.cncs = cncs;
    }

    public String getBasic() {
        return basic;
    }

    public void setBasic(String basic) {
        this.basic = basic;
    }

    public String getW3c() {
        return w3c;
    }

    public void setW3c(String w3c) {
        this.w3c = w3c;
    }

    public String getNcs() {
        return ncs;
    }

    public void setNcs(String ncs) {
        this.ncs = ncs;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Integer[] getRgb() {
        return rgb;
    }

    public void setRgb(Integer[] rgb) {
        this.rgb = rgb;
    }
}
