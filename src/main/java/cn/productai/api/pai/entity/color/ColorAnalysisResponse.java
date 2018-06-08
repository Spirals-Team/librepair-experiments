package cn.productai.api.pai.entity.color;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ColorAnalysisResponse extends BaseResponse {

    @JsonProperty("results")
    private ColorAnalysisResult[] results;

    public ColorAnalysisResult[] getResults() {
        return results;
    }

    public void setResults(ColorAnalysisResult[] results) {
        this.results = results;
    }
}
