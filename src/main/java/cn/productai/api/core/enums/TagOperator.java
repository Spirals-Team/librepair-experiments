package cn.productai.api.core.enums;

import cn.productai.api.core.attribute.EnumDescriptionAttribute;

/**
 * Created by Thinkpad on 2017/7/3.
 */
public enum TagOperator {
    @EnumDescriptionAttribute(Text = "and")
    AND,

    @EnumDescriptionAttribute(Text = "or")
    OR
}
