package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.ServiceTypeId;
import cn.productai.api.pai.base.ProductBatchDeleteByFileBaseRequest;

public class DeleteProductsRequest extends ProductBatchDeleteByFileBaseRequest<DeleteProductsResponse> {

    private String productSetId;

    private String[] productIds;

    public DeleteProductsRequest() {
        super();
        this.setRequestMethod(HttpMethod.POST);
    }

    /**
     * @param productSetId 商品集ID
     */
    public DeleteProductsRequest(String productSetId) {
        this();
        this.productSetId = productSetId;
        this.setProductIds(productIds);
    }

    @Override
    public Class<DeleteProductsResponse> getResponseClass() {
        return DeleteProductsResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("%s://%s/product_sets/%s/%s/products",
                this.getScheme(), this.getHost(), ServiceTypeId.PRODUCT_SET, this.getProductSetId());
    }

    public String getProductSetId() {
        return productSetId;
    }

    public void setProductSetId(String productSetId) {
        this.productSetId = productSetId;
    }

    public String[] getProductIds() {
        return productIds;
    }

    public void setProductIds(String[] productIds) {
        this.productIds = productIds;
    }
}
