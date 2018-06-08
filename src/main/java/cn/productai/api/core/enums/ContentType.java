package cn.productai.api.core.enums;

import cn.productai.api.core.attribute.EnumDescriptionAttribute;

/**
 * Created by Zhong Wang on 2017/6/30.
 */

public enum ContentType {

    /*
    application/x-www-form-urlencoded
     */
    @EnumDescriptionAttribute(Text = "application/x-www-form-urlencoded; charset=UTF-8")
    Default,

    /*
    application/json
     */
    @EnumDescriptionAttribute(Text = "application/json; charset=UTF-8")
    Json,

    /*
    multipart/form-data
     */
    @EnumDescriptionAttribute(Text = "multipart/form-data;")
    FormData
}
