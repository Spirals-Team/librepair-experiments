package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.ManagementAPIBaseRequest;
import cn.productai.api.core.enums.ContentType;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.ServiceTypeId;

public class GetProductsByProductSetRequest extends ManagementAPIBaseRequest<GetProductsByProductSetResponse> {

    private String productSetId;

    private String[] productIds;

    private String ids;

    public GetProductsByProductSetRequest(String productSetId, String[] productIds) {
        super();
        this.setProductSetId(productSetId);
        this.setProductIds(productIds);
        this.setIds(String.join(",", productIds));

        this.setRequestMethod(HttpMethod.GET);
        this.setContentType(ContentType.Default);
    }

    @Override
    public Class<GetProductsByProductSetResponse> getResponseClass() {
        return GetProductsByProductSetResponse.class;
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
