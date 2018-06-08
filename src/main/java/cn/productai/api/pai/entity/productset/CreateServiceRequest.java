package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.ManagementAPIBaseRequest;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.ServiceTypeId;

public class CreateServiceRequest extends ManagementAPIBaseRequest<CreateServiceResponse> {

    @ParaSignAttribute(Name = "name")
    public String name;

    @ParaSignAttribute(Name = "scenario")
    public String scenario;

    private String productSetId;

    public CreateServiceRequest() {
        super();
        this.setRequestMethod(HttpMethod.POST);
    }

    /**
     * @param name     the name of the dataset
     * @param scenario the scenario
     */
    public CreateServiceRequest(String productSetId, String name, String scenario) {
        this();
        this.setProductSetId(productSetId);
        this.setName(name);
        this.setScenario(scenario);
    }

    @Override
    public Class<CreateServiceResponse> getResponseClass() {
        return CreateServiceResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("%s://%s/product_sets/%s/%s/services",
                this.getScheme(), this.getHost(), ServiceTypeId.PRODUCT_SET, this.getProductSetId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getProductSetId() {
        return productSetId;
    }

    public void setProductSetId(String productSetId) {
        this.productSetId = productSetId;
    }
}
