package spoon.processing;


public class ProcessorPropertiesImpl implements spoon.processing.ProcessorProperties {
    private final java.util.Map<java.lang.String, java.lang.Object> _properties = new java.util.HashMap<>();

    public <T> T get(java.lang.Class<T> type, java.lang.String name) {
        if (type.isPrimitive()) {
            type = ((java.lang.Class<T>) (org.apache.commons.lang3.ClassUtils.primitiveToWrapper(type)));
        }
        T result = ((T) (_properties.get(name)));
        if (result == null) {
            return null;
        }else {
            return type.isAssignableFrom(result.getClass()) ? result : null;
        }
    }

    public void set(java.lang.String name, java.lang.Object o) {
        _properties.put(name, o);
    }

    public java.lang.String getProcessorName() {
        return ((java.lang.String) (_properties.get("__NAME__")));
    }
}

