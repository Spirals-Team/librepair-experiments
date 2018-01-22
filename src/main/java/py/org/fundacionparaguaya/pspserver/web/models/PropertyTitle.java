package py.org.fundacionparaguaya.pspserver.web.models;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by rodrigovillalba on 10/24/17.
 */
public class PropertyTitle extends ForwardingMap<String, String> {

    private Map<String,String> delegate = Maps.newHashMap();

    @Override
    protected Map<String, String> delegate() {
        return delegate;
    }
}
