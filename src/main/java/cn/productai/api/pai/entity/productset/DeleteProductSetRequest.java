package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.ServiceTypeId;

public class DeleteProductSetRequest extends BaseRequest<DeleteProductSetResponse> {

    private String productSetId;

    public DeleteProductSetRequest() {
        super();
        this.setRequestMethod(HttpMethod.DELETE);
    }

    /**
     * @param productSetId 商品集ID
     */
    public DeleteProductSetRequest(String productSetId) {
        this();
        this.setProductSetId(productSetId);
    }

    @Override
    public Class<DeleteProductSetResponse> getResponseClass() {
        return DeleteProductSetResponse.class;
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
}
