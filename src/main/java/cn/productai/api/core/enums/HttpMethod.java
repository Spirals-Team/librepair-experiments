package cn.productai.api.core.enums;

import cn.productai.api.core.attribute.EnumDescriptionAttribute;

/**
 * Created by Zhong Wang on 2017/6/30.
 */
public enum HttpMethod {

    /*
    GET
     */
    @EnumDescriptionAttribute(Text = "GET")
    GET,

    /*
    POST
     */
    @EnumDescriptionAttribute(Text = "POST")
    POST,

    /*
    PUT
     */
    @EnumDescriptionAttribute(Text = "PUT")
    PUT,

    /*
    PATCH
    */
    @EnumDescriptionAttribute(Text = "PATCH")
    PATCH,

    /*
    DELETE
     */
    @EnumDescriptionAttribute(Text = "DELETE")
    DELETE
}
