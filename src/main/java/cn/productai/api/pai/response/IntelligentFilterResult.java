package cn.productai.api.pai.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class IntelligentFilterResult {

    @JsonProperty("url")
    private String imageUrl;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("score")
    private double score;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
