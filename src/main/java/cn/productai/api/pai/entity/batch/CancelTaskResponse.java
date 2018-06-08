package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CancelTaskResponse extends BaseResponse {

    @JsonProperty("result")
    private CancelTaskResult result;

    public CancelTaskResult getResult() {
        return result;
    }

    public void setResult(CancelTaskResult result) {
        this.result = result;
    }
}
