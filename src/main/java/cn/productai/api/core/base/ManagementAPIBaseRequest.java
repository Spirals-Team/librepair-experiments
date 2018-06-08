package cn.productai.api.core.base;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.enums.ContentType;
import cn.productai.api.core.helper.WebQueryHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.misc.BASE64Encoder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ManagementAPIBaseRequest<T extends BaseResponse> extends BaseRequest<T> {

    public ManagementAPIBaseRequest() {
        super();

        this.setContentType(ContentType.Json);
    }

    @Override
    public String getQueryString() {
        // 字典
        HashMap<String, Object> dics = new HashMap<>();

        ArrayList<String> list = new ArrayList<>();
        Field[] ps = this.getClass().getFields();
        for (Field p : ps) {
            ParaSignAttribute ca = p.getAnnotation(ParaSignAttribute.class);
            if (ca != null) {
                try {
                    Object value = p.get(this);
                    if (value != null && !value.toString().isEmpty())
                        dics.put(ca.Name(), value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.getOptions() != null && this.getOptions().size() > 0) {
            for (String key : this.getOptions().keySet()) {
                try {
                    dics.put(key, this.getOptions().get(key));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 序列化为Json
        try {
            return new ObjectMapper().writeValueAsString(dics);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "{}";
    }
}
