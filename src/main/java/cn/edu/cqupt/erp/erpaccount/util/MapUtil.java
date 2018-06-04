package cn.edu.cqupt.erp.erpaccount.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个类是 Map 的工具类，在 Controller 当中返回的 Json 数据格式按照如下
 * status 表示执行方法的状态
 * message 表示错误的信息
 * object 如果前台需要，返回给前台的类
 */
public class MapUtil {

    /**
     * 这是一个静态方法，抽象了生成Map的过程提高了代码的利用率
     * @param success
     * @param message
     * @param object
     * @return
     */
    public static Map toMap(boolean success, String message, Object object){
        Map map = new HashMap();
        map.put("success",success);
        map.put("message",message);
        map.put("object",object);
        return map;
    }

}