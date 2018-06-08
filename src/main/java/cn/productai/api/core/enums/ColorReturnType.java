package cn.productai.api.core.enums;

import cn.productai.api.core.attribute.EnumDescriptionAttribute;

public enum ColorReturnType {

    /**
     * basic
     */
    @EnumDescriptionAttribute(Text = "basic")
    Basic,

    /**
     * W3C的CSS工作组发布CSS颜色模块
     */
    @EnumDescriptionAttribute(Text = "w3c")
    W3C,

    /**
     * NCS色卡是来自瑞典的色彩设计工具，它以眼睛看颜色的方式来描述颜色。表面颜色定义在NCS色卡中，同时给出一个色彩编号。
     */
    @EnumDescriptionAttribute(Text = "ncs")
    NCS,

    /**
     * CNCS是由中国纺织信息中心联合国际国内色彩专家和机构，经多年精心调研而开发的色彩体系，力求为服装设计师和相关机构提供当前最时尚的色彩信息和色彩管理解决方案。
     */
    @EnumDescriptionAttribute(Text = "cncs")
    CNCS
}
