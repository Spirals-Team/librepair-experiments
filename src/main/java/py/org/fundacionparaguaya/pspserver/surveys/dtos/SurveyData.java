package py.org.fundacionparaguaya.pspserver.surveys.dtos;

import com.google.common.collect.ForwardingMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigovillalba on 10/5/17.
 */
public class SurveyData extends ForwardingMap<String, Object> {

    private Map<String, Object> dataMap;

    private Long id;

    public SurveyData() {
        this.dataMap = new HashMap<>();
    }

    public SurveyData(Map<String, Object> collect) {
        this.dataMap = collect;

    }

    @Override
    protected Map<String, Object> delegate() {
        return dataMap;
    }

    public String getAsString(String key) {
        Object obj = this.get(key);
        if (obj != null && obj instanceof String) {
            return String.valueOf((String) obj);
        }
        return null;
    }

    public Long getId() {
        return id;
    }
}
