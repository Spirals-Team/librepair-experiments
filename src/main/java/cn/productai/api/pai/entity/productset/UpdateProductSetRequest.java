package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.ManagementAPIBaseRequest;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.ServiceTypeId;

public class UpdateProductSetRequest extends ManagementAPIBaseRequest<UpdateProductSetResponse> {

    @ParaSignAttribute(Name = "name")
    public String name;

    @ParaSignAttribute(Name = "description")
    public String description;

    private String productSetId;

    public UpdateProductSetRequest() {
        super();
        this.setRequestMethod(HttpMethod.PUT);
    }

    /**
     * @param productSetId 商品集ID
     * @param name         商品集名称
     * @param description  商品集描述
     */
    public UpdateProductSetRequest(String productSetId, String name, String description) {
        this();
        this.setProductSetId(productSetId);
        this.setName(name);
        this.setDescription(description);
    }

    @Override
    public Class<UpdateProductSetResponse> getResponseClass() {
        return UpdateProductSetResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("%s://%s/product_sets/%s/%s",
                this.getScheme(), this.getHost(), ServiceTypeId.PRODUCT_SET, this.getProductSetId());
    }

    public String getProductSetId() {
        return productSetId;
    }

    public void setProductSetId(String productSetId) {
        this.productSetId = productSetId;
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
