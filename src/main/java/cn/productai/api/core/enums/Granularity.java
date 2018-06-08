package cn.productai.api.core.enums;

import cn.productai.api.core.attribute.EnumDescriptionAttribute;

public enum Granularity {

    /**
     * 只返回整个图片最主要的两种颜色
     */
    @EnumDescriptionAttribute(Text = "major")
    Major,

    /**
     * 返回图片内更多的颜色
     */
    @EnumDescriptionAttribute(Text = "detailed")
    Detailed,

    /**
     * 只返回一个图片主颜色
     */
    @EnumDescriptionAttribute(Text = "dominant")
    Dominant
}
