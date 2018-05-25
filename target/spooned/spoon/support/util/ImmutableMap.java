package spoon.support.util;


public interface ImmutableMap {
    boolean hasValue(java.lang.String parameterName);

    java.lang.Object getValue(java.lang.String parameterName);

    spoon.support.util.ImmutableMap putValue(java.lang.String parameterName, java.lang.Object value);

    java.util.Map<java.lang.String, java.lang.Object> asMap();

    spoon.support.util.ImmutableMap checkpoint();

    java.util.Map<java.lang.String, java.lang.Object> getModifiedValues();
}

