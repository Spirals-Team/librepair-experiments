package cn.productai.api.core.helper;

import cn.productai.api.core.attribute.EnumDescriptionAttribute;
import cn.productai.api.core.attribute.ServiceDescriptionAttribute;
import cn.productai.api.core.entity.AIService;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.lang.Class;

/**
 * Created by Zhong Wang on 2017/6/30.
 *
 */

public final class EnumHelper {
    public static HashMap<Integer, String> toHashMap(Class _enumType) {

        if (!_enumType.isEnum()) {
            throw new IllegalArgumentException("Only support enum type!");
        }
        HashMap<Integer, String> dic = new HashMap<Integer, String>();

        Field[] ps = _enumType.getFields();
        for (Field p : ps) {
            if (!p.getType().equals(_enumType)) {
                continue;
            }
            Enum _enum = Enum.valueOf(_enumType, p.getName());
            EnumDescriptionAttribute att = p.getAnnotation(EnumDescriptionAttribute.class);
            if (att != null) {
                dic.put(_enum.ordinal(), att.Text());
            } else {
                dic.put(_enum.ordinal(), p.getName());
            }
        }
        return dic;
    }

    public static HashMap<Integer, AIService> toServiceHashMap(Class _enumType){
        if (!_enumType.isEnum()) {
            throw new IllegalArgumentException("Only support enum type!");
        }
        HashMap<Integer, AIService> dic = new HashMap<Integer, AIService>();

        Field[] ps = _enumType.getFields();
        for(Field p : ps){
            if (!p.getType().equals(_enumType)) {
                continue;
            }
            Enum _enum = Enum.valueOf(_enumType, p.getName());
            ServiceDescriptionAttribute att = p.getAnnotation(ServiceDescriptionAttribute.class);
            if (att != null) {
                AIService newService = new AIService();
                newService.setName(att.Name());
                newService.setServiceType(att.ServiceType());
                newService.setServiceId(att.ServiceId());

                dic.put(_enum.ordinal(), newService);
            }
            else {
                throw new IllegalArgumentException(String.format("%s has no ServiceDescriptionAttribute", p.getName()));
            }
        }
        return dic;
    }
}
