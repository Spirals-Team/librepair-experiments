package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetProductsByProductSetResponse extends BaseResponse {

    @JsonProperty("results")
    private Product[] products;

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }
}
