package cn.productai.api.pai.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassifyResult {
    @JsonProperty("category")
    private String category;

    @JsonProperty("score")
    private String score;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
