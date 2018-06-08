package cn.productai.api.pai.base;

import cn.productai.api.core.attribute.IgnoreExtraParasAttribute;
import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.base.BaseResponse;

import java.util.ArrayList;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
@IgnoreExtraParasAttribute
public abstract class DataSetSingleModifyByUrlBaseRequest<T extends BaseResponse> extends BaseRequest<T> {

    private String imageSetId = "";

    @Override
    public String getApiUrl() {
        return String.format("https://%s/image_sets/_0000014/%s", this.getHost(), this.imageSetId);
    }

    /**
     * your image url
     */
    @ParaSignAttribute(Name = "image_url", IsNeedUrlEncode = true)
    public String imageUrl;

    /**
     * search tags
     */
    private ArrayList<String> searchTags = null;

    /**
     * tags
     */
    @ParaSignAttribute(Name = "tags", IsNeedUrlEncode = true)
    public String Tags;

    public String getImageSetId() {
        return imageSetId;
    }

    public void setImageSetId(String imageSetId) {
        this.imageSetId = imageSetId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getSearchTags() {
        return searchTags;
    }

    /**
     * set your search tags
     * @param searchTags search tag array
     */
    public void setSearchTags(ArrayList<String> searchTags) {
        this.searchTags = searchTags;
        Tags = this.getTags();
    }

    public String getTags() {
        if (this.searchTags != null && this.searchTags.size() > 0) {
            return String.join("|", this.searchTags);
        }

        return null;
    }

    /**
     * meta info
     */
    @ParaSignAttribute(Name = "meta", IsNeedUrlEncode = true)
    public String meta = null;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    /**
     *
     * @param imageSetId your image set id
     * @param searchTags your search tags
     * @param meta your meta info
     */
    public DataSetSingleModifyByUrlBaseRequest(String imageSetId, ArrayList<String> searchTags, String meta) {
        super();
        if (imageSetId == null || imageSetId.isEmpty())
            throw new IllegalArgumentException("imageSetId can not be null!");

        this.setImageSetId(imageSetId);
        this.setSearchTags(searchTags);
        this.setMeta(meta);
    }

    /**
     *
     * @param imageSetId your image set id
     * @param imageUrl your image url
     * @param searchTags your search tags
     * @param meta your meta info
     */
    public DataSetSingleModifyByUrlBaseRequest(String imageSetId, String imageUrl, ArrayList<String> searchTags, String meta) {
        this(imageSetId, searchTags, meta);
        this.setImageUrl(imageUrl);
    }

    public DataSetSingleModifyByUrlBaseRequest(){
        super();
    }
}
