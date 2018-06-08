package cn.productai.api.pai.entity.dataset;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAllDataSetsResponse extends BaseResponse {

    @JsonProperty("results")
    private Dataset[] Datasets;

    public Dataset[] getDatasets() {
        return Datasets;
    }

    public void setDatasets(Dataset[] datasets) {
        Datasets = datasets;
    }
}
