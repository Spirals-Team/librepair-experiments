package cn.productai.api.pai.entity.service;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class DeleteServiceRequest extends BaseRequest<DeleteServiceResponse> {

    @Override
    public Class<DeleteServiceResponse> getResponseClass() {
        return DeleteServiceResponse.class;
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

    public DeleteServiceRequest() {
        super();
        this.setRequestMethod(HttpMethod.DELETE);
    }

    /**
     *
     * @param serviceId 服务ID
     */
    public DeleteServiceRequest(String serviceId) {
        this();
        this.setServiceId(serviceId);
    }
}
