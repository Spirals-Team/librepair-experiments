package cn.productai.api.pai.base;

import cn.productai.api.core.ITag;
import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.BaseResponse;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public abstract class SearchByImageUrlBaseRequest<T extends BaseResponse> extends CallApiByImageUrlBaseRequest<T> {

    /**
     * search tags
     */
    private ITag searchTag;

    @ParaSignAttribute(Name = "tags", IsNeedUrlEncode = true)
    public String tags = "";

    public ITag getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(ITag searchTag) {
        this.searchTag = searchTag;
        this.tags = this.getTags();
    }

    public String getTags() {
        return this.searchTag == null ? "" : this.searchTag.toString();
    }

    /**
     * the max number of results you want to contain in the result response
     */
    @ParaSignAttribute(Name = "count")
    public Integer count = 10;

    public Integer getCount() {
        return count;
    }

    /**
     * set the max number of results you want to contain in the result response
     *
     * @param count the max number of results you want to contain in the result response. 0-100
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    public SearchByImageUrlBaseRequest(String serviceType, String serviceId) {
        super(serviceType, serviceId);
    }

    public SearchByImageUrlBaseRequest(String serviceType, String serviceId, String url) {
        super(serviceType, serviceId, url);
    }

    public SearchByImageUrlBaseRequest(String serviceType, String serviceId, String url, String loc, ITag searchTag, Integer count) {
        super(serviceType, serviceId, url, loc);

        this.searchTag = searchTag;
        this.tags = this.getTags();

        if (count != null && count > 0 && count <= 100)
            this.count = count;
    }

    public SearchByImageUrlBaseRequest(){
        super();
    }
}
