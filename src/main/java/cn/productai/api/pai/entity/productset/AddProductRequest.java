package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.ManagementAPIBaseRequest;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.ServiceTypeId;

public class AddProductRequest extends ManagementAPIBaseRequest<AddProductResponse> {

    @ParaSignAttribute(Name = "product_id")
    public String productId;

    @ParaSignAttribute(Name = "price")
    public String price;

    @ParaSignAttribute(Name = "keywords")
    public String keywords;

    @ParaSignAttribute(Name = "image_url")
    public String imageUrl;

    @ParaSignAttribute(Name = "meta")
    public String meta;

    @ParaSignAttribute(Name = "tags")
    public String tags;

    private String productSetId;

    public AddProductRequest() {
        super();
        this.setRequestMethod(HttpMethod.POST);
    }

    /**
     * @param productSetId 商品集 ID
     * @param productId    商品 ID
     * @param price        商品价格
     * @param keywords     商品关键字
     * @param imageUrl     商品图片 URL
     * @param meta         商品描述信息
     * @param tags         商品标签
     */
    public AddProductRequest(String productSetId,
                             String productId,
                             String price,
                             String keywords,
                             String imageUrl,
                             String meta,
                             String tags) {
        this();
        this.setProductSetId(productSetId);
        this.setProductId(productId);
        this.setPrice(price);
        this.setKeywords(keywords);
        this.setImageUrl(imageUrl);
        this.setMeta(meta);
        this.setTags(tags);
    }

    @Override
    public Class<AddProductResponse> getResponseClass() {
        return AddProductResponse.class;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
