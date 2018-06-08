package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.ServiceTypeId;

public class GetProductSetRequest extends BaseRequest<GetProductSetResponse> {

    private String productSetId;

    /**
     * @param productSetId 商品集 ID
     */
    public GetProductSetRequest(String productSetId) {
        super();
        this.setProductSetId(productSetId);
        this.setRequestMethod(HttpMethod.GET);
    }

    @Override
    public Class<GetProductSetResponse> getResponseClass() {
        return GetProductSetResponse.class;
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
