package cn.productai.api.pai.entity.service;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class GetServiceInfoRequest extends BaseRequest<GetServiceInfoResponse> {

    @Override
    public Class<GetServiceInfoResponse> getResponseClass() {
        return GetServiceInfoResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/customer_services/_0000172/%s", this.getHost(), this.getServiceId());
    }

    private String serviceId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public GetServiceInfoRequest() {
        super();
        this.setRequestMethod(HttpMethod.GET);
    }

    /**
     *
     * @param serviceId 服务ID
     */
    public GetServiceInfoRequest(String serviceId) {
        this();
        this.setServiceId(serviceId);
    }
}
