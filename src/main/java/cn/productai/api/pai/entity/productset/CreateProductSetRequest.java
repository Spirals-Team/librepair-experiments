package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.ManagementAPIBaseRequest;
import cn.productai.api.core.enums.ServiceTypeId;

public class CreateProductSetRequest extends ManagementAPIBaseRequest<CreateProductSetResponse> {

    @ParaSignAttribute(Name = "name")
    public String name;

    @ParaSignAttribute(Name = "description")
    public String description;

    /**
     *
     */
    public CreateProductSetRequest() {
        super();
    }

    /**
     * @param name        the name of the dataset
     * @param description the description of the dataset
     */
    public CreateProductSetRequest(String name, String description) {
        this();
        this.setName(name);
        this.setDescription(description);
    }

    @Override
    public Class<CreateProductSetResponse> getResponseClass() {
        return CreateProductSetResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("%s://%s/product_sets/%s", this.getScheme(), this.getHost(), ServiceTypeId.PRODUCT_SET);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
