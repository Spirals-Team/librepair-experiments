package py.org.fundacionparaguaya.pspserver.surveys.entities;

import com.google.common.collect.ForwardingList;

import java.util.List;

/**
 * Created by rodrigovillalba on 10/16/17.
 */
public class Properties extends ForwardingList<PropertyAttributeEntity> {

    private final List<PropertyAttributeEntity> delegate;


    private Properties(List<PropertyAttributeEntity> delegate) {
        this.delegate = delegate;
    }

    public static Properties of(List<PropertyAttributeEntity> delegate) {
        return new Properties(delegate);
    }

    @Override
    protected List<PropertyAttributeEntity> delegate() {
        return delegate;
    }


}
