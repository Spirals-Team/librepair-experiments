package spoon.pattern.internal.parameter;


public class ListParameterInfo extends spoon.pattern.internal.parameter.AbstractParameterInfo {
    private final int idx;

    public ListParameterInfo(spoon.pattern.internal.parameter.ParameterInfo next) {
        this((-1), next);
    }

    public ListParameterInfo(int idx, spoon.pattern.internal.parameter.ParameterInfo next) {
        super(next);
        this.idx = idx;
    }

    @java.lang.Override
    protected java.lang.String getPlainName() {
        return getWrappedName(getContainerName());
    }

    @java.lang.Override
    protected java.lang.String getWrappedName(java.lang.String containerName) {
        if ((idx) < 0) {
            return containerName;
        }
        return ((containerName + "[") + (idx)) + "]";
    }

    @java.lang.Override
    protected java.lang.Object addValueAs(java.lang.Object container, java.util.function.Function<java.lang.Object, java.lang.Object> merger) {
        java.util.List<java.lang.Object> list = castTo(container, java.util.List.class);
        java.lang.Object existingValue = getExistingValue(list);
        java.lang.Object newValue = merger.apply(existingValue);
        if (newValue == (spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE)) {
            return spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE;
        }
        if (existingValue == newValue) {
            return list;
        }
        if (newValue == null) {
            return list;
        }
        java.util.List<java.lang.Object> newList = new java.util.ArrayList<>(((list.size()) + 1));
        newList.addAll(list);
        if ((idx) >= 0) {
            while ((idx) >= (newList.size())) {
                newList.add(null);
            } 
            newList.set(idx, newValue);
        }else {
            if (newValue instanceof java.util.Collection) {
                newList.addAll(((java.util.Collection) (newValue)));
            }else {
                newList.add(newValue);
            }
        }
        return java.util.Collections.unmodifiableList(newList);
    }

    protected java.lang.Object getExistingValue(java.util.List<java.lang.Object> list) {
        if (((list == null) || ((idx) < 0)) || ((idx) >= (list.size()))) {
            return null;
        }
        return list.get(idx);
    }

    @java.lang.Override
    protected java.util.List<java.lang.Object> getEmptyContainer() {
        return java.util.Collections.emptyList();
    }

    @java.lang.Override
    protected java.lang.Object getValue(spoon.support.util.ImmutableMap parameters) {
        java.util.List<java.lang.Object> list = castTo(super.getValue(parameters), java.util.List.class);
        if ((idx) < 0) {
            return list;
        }
        if ((idx) < (list.size())) {
            return list.get(idx);
        }
        return null;
    }

    @java.lang.Override
    protected <T> T castTo(java.lang.Object o, java.lang.Class<T> type) {
        if (o instanceof java.util.Set) {
            o = new java.util.ArrayList(((java.util.Set) (o)));
        }else
            if (o instanceof java.lang.Object[]) {
                o = java.util.Arrays.asList(((java.lang.Object[]) (o)));
            }

        return super.castTo(o, type);
    }
}

