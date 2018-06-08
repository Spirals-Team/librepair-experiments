package cn.productai.api.core.entity;

import cn.productai.api.core.ISearchTag;
import cn.productai.api.core.ITag;

/**
 * Created by Thinkpad on 2017/7/3.
 */
public class SearchTag implements ITag {
    private ISearchTag tag;

    @Override
    public void setTag(ISearchTag tag) {
        this.tag = tag;
    }

    @Override
    public ISearchTag getTag() {
        return this.tag;
    }

    @Override
    public String toString() {
        return String.format("{%s}", this.tag.toString());
    }
}
