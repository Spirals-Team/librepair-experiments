package spoon.support.util;


@spoon.support.Internal
public class ImmutableMapImpl implements spoon.support.util.ImmutableMap {
    protected final spoon.support.util.ImmutableMap parent;

    protected final java.util.Map<java.lang.String, java.lang.Object> map;

    public ImmutableMapImpl(java.util.Map<java.lang.String, java.lang.Object> map) {
        this(null, map);
    }

    private ImmutableMapImpl(spoon.support.util.ImmutableMap parent, java.util.Map<java.lang.String, java.lang.Object> map) {
        this.parent = parent;
        this.map = java.util.Collections.unmodifiableMap(map);
    }

    public ImmutableMapImpl(java.util.Map<java.lang.String, java.lang.Object> map, java.lang.String parameterName, java.lang.Object value) {
        this(null, map, parameterName, value);
    }

    private ImmutableMapImpl(spoon.support.util.ImmutableMap parent, java.util.Map<java.lang.String, java.lang.Object> map, java.lang.String parameterName, java.lang.Object value) {
        this.parent = null;
        java.util.Map<java.lang.String, java.lang.Object> copy = new java.util.HashMap<>(((map.size()) + 1));
        copy.putAll(map);
        copy.put(parameterName, value);
        this.map = java.util.Collections.unmodifiableMap(copy);
    }

    public ImmutableMapImpl() {
        this.parent = null;
        this.map = java.util.Collections.emptyMap();
    }

    @java.lang.Override
    public spoon.support.util.ImmutableMapImpl checkpoint() {
        return new spoon.support.util.ImmutableMapImpl(this, java.util.Collections.emptyMap());
    }

    @java.lang.Override
    public boolean hasValue(java.lang.String parameterName) {
        if (map.containsKey(parameterName)) {
            return true;
        }
        if ((parent) != null) {
            return parent.hasValue(parameterName);
        }
        return false;
    }

    @java.lang.Override
    public java.lang.Object getValue(java.lang.String parameterName) {
        java.lang.Object v = map.get(parameterName);
        if ((v == null) && ((parent) != null)) {
            v = parent.getValue(parameterName);
        }
        return v;
    }

    @java.lang.Override
    public spoon.support.util.ImmutableMap putValue(java.lang.String parameterName, java.lang.Object value) {
        return new spoon.support.util.ImmutableMapImpl(parent, map, parameterName, value);
    }

    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        spoon.support.util.ImmutableMapImpl.appendMap(sb, map);
        if ((parent) != null) {
            sb.append("\nparent:\n");
            sb.append(parent.toString());
        }
        return sb.toString();
    }

    private static void appendMap(java.lang.StringBuilder sb, java.util.Map<java.lang.String, java.lang.Object> map) {
        java.util.List<java.lang.String> paramNames = new java.util.ArrayList<>(map.keySet());
        paramNames.sort(( a, b) -> a.compareTo(b));
        for (java.lang.String name : paramNames) {
            if ((sb.length()) > 0) {
                sb.append("\n");
            }
            sb.append(name).append('=').append(map.get(name));
        }
    }

    @java.lang.Override
    public java.util.Map<java.lang.String, java.lang.Object> asMap() {
        if ((parent) != null) {
            java.util.Map<java.lang.String, java.lang.Object> merged = new java.util.HashMap<>();
            merged.putAll(parent.asMap());
            merged.putAll(map);
            return java.util.Collections.unmodifiableMap(merged);
        }
        return map;
    }

    @java.lang.Override
    public java.util.Map<java.lang.String, java.lang.Object> getModifiedValues() {
        return map;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        if (obj instanceof spoon.support.util.ImmutableMap) {
            obj = ((spoon.support.util.ImmutableMap) (obj)).asMap();
        }
        if (obj instanceof java.util.Map) {
            java.util.Map map = ((java.util.Map) (obj));
            return asMap().equals(map);
        }
        return false;
    }
}

