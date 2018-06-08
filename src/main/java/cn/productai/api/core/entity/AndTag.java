package cn.productai.api.core.entity;

import cn.productai.api.core.enums.TagOperator;

/**
 * Created by Thinkpad on 2017/7/3.
 */
public class AndTag extends TagBase {

    public AndTag() {
        super(TagOperator.AND);
    }
}
