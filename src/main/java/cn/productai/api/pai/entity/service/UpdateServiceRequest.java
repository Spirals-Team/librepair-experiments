package cn.productai.api.pai.entity.service;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.ManagementAPIBaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class UpdateServiceRequest extends ManagementAPIBaseRequest<UpdateServiceResponse> {

    @Override
    public Class<UpdateServiceResponse> getResponseClass() {
        return UpdateServiceResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/customer_services/_0000172/%s", this.getHost(), this.getServiceId());
    }

    private String serviceId;

    @ParaSignAttribute(Name = "name")
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public UpdateServiceRequest() {
        super();
        this.setRequestMethod(HttpMethod.PUT);
    }

    /**
     *
     * @param serviceId 服务ID
     * @param name 服务名称
     */
    public UpdateServiceRequest(String serviceId, String name) {
        this();
        this.setServiceId(serviceId);
        this.setName(name);
    }
}
