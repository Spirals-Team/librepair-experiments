package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSupportServicesResponse extends BaseResponse {

    @JsonProperty("data")
    private String[] supportServiceIds;

    public String[] getSupportServiceIds() {
        return supportServiceIds;
    }

    public void setSupportServiceIds(String[] supportServiceIds) {
        this.supportServiceIds = supportServiceIds;
    }
}
