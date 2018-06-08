package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.ManagementAPIBaseRequest;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.ServiceTypeId;

public class DeleteProductsByProductSetRequest extends ManagementAPIBaseRequest<DeleteProductsByProductSetResponse> {

    private String productSetId;

    private String[] productIds;

    private String ids;

    public DeleteProductsByProductSetRequest() {
        super();
        this.setRequestMethod(HttpMethod.DELETE);
    }

    /**
     * @param productSetId 商品集ID
     */
    public DeleteProductsByProductSetRequest(String productSetId, String[] productIds) {
        this();
        this.setProductSetId(productSetId);
        this.setProductIds(productIds);
        this.setProductSetId(productSetId);
        this.setIds(String.join(",", productIds));
    }

    @Override
    public Class<DeleteProductsByProductSetResponse> getResponseClass() {
        return DeleteProductsByProductSetResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("%s://%s/product_sets/%s/%s/products?ids=%s",
                this.getScheme(), this.getHost(), ServiceTypeId.PRODUCT_SET, this.getProductSetId(), this.getIds());
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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
