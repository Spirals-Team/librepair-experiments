package cn.productai.api.pai.base;

import cn.productai.api.core.ITag;
import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.BaseResponse;

import java.io.File;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public abstract class SearchByImageFileBaseRequest<T extends BaseResponse> extends CallApiByImageFileBaseRequest<T> {

    /**
     * advanced search tag
     */
    private ITag searchTag = null;

    @ParaSignAttribute(Name = "tags", IsNeedUrlEncode = true)
    public String tags = null;

    public String getTags() {
        if (this.searchTag == null)
            return null;
        return this.searchTag.toString();
    }

    public ITag getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(ITag searchTag) {
        this.searchTag = searchTag;
        this.tags = this.getTags();
    }

    @ParaSignAttribute(Name = "count")
    public Integer count = 10;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public SearchByImageFileBaseRequest(String serviceType, String serviceId) {
        super(serviceType, serviceId);
    }

    public SearchByImageFileBaseRequest(String serviceType, String serviceId, String loc, ITag searchTag, Integer count) {
        super(serviceType, serviceId, loc);
        this.searchTag = searchTag;
        this.tags = this.getTags();
        if (count != null && count > 0 && count <= 100)
            this.count = count;
    }

    public SearchByImageFileBaseRequest(String serviceType, String serviceId, File imageFile, String loc, ITag searchTag, Integer count) {
        this(serviceType, serviceId, loc, searchTag, count);
        this.setImageFile(imageFile);
    }

    public SearchByImageFileBaseRequest(){
        super();
    }
}
