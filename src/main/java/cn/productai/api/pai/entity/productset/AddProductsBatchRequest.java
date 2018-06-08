package cn.productai.api.pai.entity.productset;

import cn.productai.api.pai.base.ProductBatchAddByFileBaseRequest;

import java.io.File;

public class AddProductsBatchRequest extends ProductBatchAddByFileBaseRequest<AddProductsBatchResponse> {

    public AddProductsBatchRequest(String productSetId) {
        super(productSetId, "products_to_add");
    }

    public AddProductsBatchRequest(String productSetId, File csvFile) {
        super(productSetId, csvFile, "products_to_add");
    }

    @Override
    public Class<AddProductsBatchResponse> getResponseClass() {
        return AddProductsBatchResponse.class;
    }
}
