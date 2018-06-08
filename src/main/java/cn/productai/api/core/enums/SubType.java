package cn.productai.api.core.enums;

import cn.productai.api.core.attribute.EnumDescriptionAttribute;

public enum SubType {
    @EnumDescriptionAttribute(Text = "everything")
    EveryThing,

    @EnumDescriptionAttribute(Text = "person_outfit")
    PersonOutfit,

    @EnumDescriptionAttribute(Text = "foreground")
    Foreground
}
