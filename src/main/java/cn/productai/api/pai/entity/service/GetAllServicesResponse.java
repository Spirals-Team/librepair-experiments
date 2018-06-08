package cn.productai.api.pai.entity.service;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAllServicesResponse extends BaseResponse {

    @JsonProperty("results")
    private SearchService[] Services;

    public SearchService[] getServices() {
        return Services;
    }

    public void setServices(SearchService[] services) {
        Services = services;
    }
}
