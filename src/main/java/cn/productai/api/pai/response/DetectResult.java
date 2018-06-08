package cn.productai.api.pai.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class DetectResult {

    @JsonProperty("box")
    private double[] BoxLocation;

    @JsonProperty("type")
    private String Type;

    @JsonProperty("puid")
    private String Puid;

    @JsonProperty("score")
    private double Score;

    public double[] getBoxLocation() {
        return BoxLocation;
    }

    public void setBoxLocation(double[] boxLocation) {
        BoxLocation = boxLocation;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(double score) {
        Score = score;
    }

    public String getPuid() {
        return Puid;
    }

    public void setPuid(String puid) {
        Puid = puid;
    }
}
