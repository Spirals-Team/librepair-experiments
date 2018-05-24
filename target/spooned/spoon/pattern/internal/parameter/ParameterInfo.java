package spoon.pattern.internal.parameter;


public interface ParameterInfo {
    int UNLIMITED_OCCURENCES = java.lang.Integer.MAX_VALUE;

    java.lang.String getName();

    spoon.support.util.ImmutableMap addValueAs(spoon.support.util.ImmutableMap parameters, java.lang.Object value);

    <T> void getValueAs(spoon.reflect.factory.Factory factory, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters);

    boolean isMultiple();

    java.lang.Class<?> getParameterValueType();

    spoon.pattern.Quantifier getMatchingStrategy();

    boolean isRepeatable();

    boolean isMandatory(spoon.support.util.ImmutableMap parameters);

    boolean isTryNextMatch(spoon.support.util.ImmutableMap parameters);
}

