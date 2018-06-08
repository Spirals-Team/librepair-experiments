package cn.productai.api.pai.entity.service;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class GetAllServicesRequest extends BaseRequest<GetAllServicesResponse> {

    @Override
    public Class<GetAllServicesResponse> getResponseClass() {
        return GetAllServicesResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/customer_services/_0000172", this.getHost());
    }

    public GetAllServicesRequest() {
        super();
        this.setRequestMethod(HttpMethod.GET);
    }
}
