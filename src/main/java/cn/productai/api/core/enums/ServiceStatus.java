package cn.productai.api.core.enums;

import cn.productai.api.core.attribute.EnumDescriptionAttribute;

public enum ServiceStatus {

    @EnumDescriptionAttribute(Text = "running")
    Running,

    @EnumDescriptionAttribute(Text = "unknown")
    UnKnown
}
