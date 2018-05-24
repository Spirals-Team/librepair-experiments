package spoon.pattern.internal.parameter;


public class SetParameterInfo extends spoon.pattern.internal.parameter.AbstractParameterInfo {
    public SetParameterInfo(spoon.pattern.internal.parameter.AbstractParameterInfo next) {
        super(next);
    }

    @java.lang.Override
    protected java.lang.String getPlainName() {
        return getWrappedName(getContainerName());
    }

    @java.lang.Override
    protected java.lang.String getWrappedName(java.lang.String containerName) {
        return containerName;
    }

    @java.lang.Override
    protected java.lang.Object addValueAs(java.lang.Object container, java.util.function.Function<java.lang.Object, java.lang.Object> merger) {
        java.util.Set<java.lang.Object> set = castTo(container, java.util.Set.class);
        java.lang.Object newValue = merger.apply(null);
        if (newValue == (spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE)) {
            return spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE;
        }
        if (newValue == null) {
            return set;
        }
        if (set.contains(newValue)) {
            return set;
        }
        java.util.Set<java.lang.Object> newSet = new java.util.LinkedHashSet<>(((set.size()) + 1));
        newSet.addAll(set);
        if (newValue instanceof java.util.Collection) {
            if ((newSet.addAll(((java.util.Collection) (newValue)))) == false) {
                return set;
            }
        }else {
            newSet.add(newValue);
        }
        return java.util.Collections.unmodifiableSet(newSet);
    }

    @java.lang.Override
    protected java.util.Set<java.lang.Object> getEmptyContainer() {
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    protected java.lang.Object getValue(spoon.support.util.ImmutableMap parameters) {
        return castTo(super.getValue(parameters), java.util.Set.class);
    }

    @java.lang.Override
    protected <T> T castTo(java.lang.Object o, java.lang.Class<T> type) {
        if (o instanceof java.util.List) {
            o = new java.util.LinkedHashSet(((java.util.List) (o)));
        }else
            if (o instanceof java.lang.Object[]) {
                o = new java.util.LinkedHashSet<>(java.util.Arrays.asList(((java.lang.Object[]) (o))));
            }

        return super.castTo(o, type);
    }
}

