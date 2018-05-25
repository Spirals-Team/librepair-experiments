package spoon.pattern.internal.parameter;


public class MapParameterInfo extends spoon.pattern.internal.parameter.AbstractParameterInfo {
    private final java.lang.String name;

    public MapParameterInfo(java.lang.String name) {
        this(name, null);
    }

    public MapParameterInfo(spoon.pattern.internal.parameter.AbstractParameterInfo next) {
        this(null, next);
    }

    public MapParameterInfo(java.lang.String name, spoon.pattern.internal.parameter.AbstractParameterInfo next) {
        super(next);
        this.name = name;
    }

    @java.lang.Override
    protected java.lang.String getPlainName() {
        return getWrappedName(getContainerName());
    }

    @java.lang.Override
    protected java.lang.String getWrappedName(java.lang.String containerName) {
        if ((name) == null) {
            return containerName;
        }
        if ((containerName.length()) > 0) {
            containerName += ".";
        }
        return containerName + (name);
    }

    @java.lang.Override
    protected java.lang.Object addValueAs(java.lang.Object container, java.util.function.Function<java.lang.Object, java.lang.Object> merger) {
        spoon.support.util.ImmutableMap parameters = castTo(container, spoon.support.util.ImmutableMap.class);
        if ((name) == null) {
            java.lang.Object newValue = merger.apply(null);
            if (newValue == null) {
                return parameters;
            }
            if (newValue == (spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE)) {
                return spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE;
            }
            if (newValue instanceof java.util.Map.Entry<?, ?>) {
                java.util.Map.Entry<?, ?> newEntry = ((java.util.Map.Entry<?, ?>) (newValue));
                java.lang.String newEntryKey = ((java.lang.String) (newEntry.getKey()));
                java.lang.Object existingValue = parameters.getValue(newEntryKey);
                java.lang.Object newEntryValue = merge(existingValue, newEntry.getValue());
                if (newEntryValue == (spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE)) {
                    return spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE;
                }
                if (existingValue == newEntryValue) {
                    return parameters;
                }
                return parameters.putValue(newEntryKey, newEntryValue);
            }
            if (newValue instanceof java.util.Map) {
                java.util.Map<java.lang.String, java.lang.Object> newMap = ((java.util.Map) (newValue));
                for (java.util.Map.Entry<java.lang.String, java.lang.Object> newEntry : newMap.entrySet()) {
                    java.lang.String newEntryKey = newEntry.getKey();
                    java.lang.Object existingValue = parameters.getValue(newEntryKey);
                    java.lang.Object newEntryValue = merge(existingValue, newEntry.getValue());
                    if (newEntryValue == (spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE)) {
                        return spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE;
                    }
                    if (existingValue != newEntryValue) {
                        parameters = parameters.putValue(newEntryKey, newEntryValue);
                    }
                }
                return parameters;
            }
            return spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE;
        }
        java.lang.Object existingValue = parameters.getValue(name);
        java.lang.Object newValue = merger.apply(existingValue);
        if (newValue == (spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE)) {
            return spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE;
        }
        if (existingValue == newValue) {
            return parameters;
        }
        return parameters.putValue(name, newValue);
    }

    @java.lang.Override
    protected java.lang.Object getValue(spoon.support.util.ImmutableMap parameters) {
        spoon.support.util.ImmutableMap map = castTo(super.getValue(parameters), spoon.support.util.ImmutableMap.class);
        return (name) == null ? map : map.getValue(name);
    }

    @java.lang.Override
    protected <T> T castTo(java.lang.Object o, java.lang.Class<T> type) {
        if (o instanceof java.util.Map) {
            o = new spoon.support.util.ImmutableMapImpl(((java.util.Map) (o)));
        }
        return super.castTo(o, type);
    }

    private static final spoon.support.util.ImmutableMap EMPTY = new spoon.support.util.ImmutableMapImpl();

    @java.lang.Override
    protected spoon.support.util.ImmutableMap getEmptyContainer() {
        return spoon.pattern.internal.parameter.MapParameterInfo.EMPTY;
    }
}

