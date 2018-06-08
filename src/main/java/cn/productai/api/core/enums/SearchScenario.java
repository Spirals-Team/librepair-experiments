package cn.productai.api.core.enums;

import cn.productai.api.core.attribute.EnumDescriptionAttribute;

public enum SearchScenario {

    @EnumDescriptionAttribute(Text = "fashion_v5_4")
    Fashion_V5_4,

    @EnumDescriptionAttribute(Text = "furniture_v6")
    Furniture_V6,

    @EnumDescriptionAttribute(Text = "wine_v2_1")
    Wine_V2_1,

    @EnumDescriptionAttribute(Text = "material")
    Material,

    @EnumDescriptionAttribute(Text = "general")
    General
}
