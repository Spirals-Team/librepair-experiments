package cn.productai.api.pai.entity.classify;


import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.pai.response.ClassifyResult;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassifyResponse extends BaseResponse {

    @JsonProperty("results")
    private ClassifyResult[] results;

    public ClassifyResult[] getResults() {
        return results;
    }

    public void setResults(ClassifyResult[] results) {
        this.results = results;
    }
}
