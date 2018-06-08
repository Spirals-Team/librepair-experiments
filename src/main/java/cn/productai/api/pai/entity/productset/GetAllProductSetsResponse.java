package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAllProductSetsResponse extends BaseResponse {

    @JsonProperty("results")
    private ProductSet[] results;

    public ProductSet[] getResults() {
        return results;
    }

    public void setResults(ProductSet[] results) {
        this.results = results;
    }
}
