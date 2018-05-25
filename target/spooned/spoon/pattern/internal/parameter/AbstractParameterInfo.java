package spoon.pattern.internal.parameter;


public abstract class AbstractParameterInfo implements spoon.pattern.internal.parameter.ParameterInfo {
    protected static final java.lang.Object NO_MERGE = new java.lang.Object();

    private final spoon.pattern.internal.parameter.AbstractParameterInfo containerItemAccessor;

    private spoon.reflect.meta.ContainerKind containerKind = null;

    private java.lang.Boolean repeatable = null;

    private int minOccurences = 0;

    private int maxOccurences = spoon.pattern.internal.parameter.ParameterInfo.UNLIMITED_OCCURENCES;

    private spoon.pattern.Quantifier matchingStrategy = spoon.pattern.Quantifier.GREEDY;

    private spoon.pattern.internal.ValueConvertor valueConvertor;

    private java.util.function.Predicate<java.lang.Object> matchCondition;

    private java.lang.Class<?> parameterValueType;

    protected AbstractParameterInfo(spoon.pattern.internal.parameter.ParameterInfo containerItemAccessor) {
        super();
        this.containerItemAccessor = ((spoon.pattern.internal.parameter.AbstractParameterInfo) (containerItemAccessor));
    }

    protected java.lang.String getContainerName() {
        if ((containerItemAccessor) != null) {
            return containerItemAccessor.getPlainName();
        }
        return "";
    }

    @java.lang.Override
    public final java.lang.String getName() {
        spoon.pattern.internal.parameter.AbstractParameterInfo cca = getContainerKindAccessor(getContainerKind(null, null));
        if (cca != null) {
            return cca.getWrappedName(getPlainName());
        }
        return getPlainName();
    }

    protected abstract java.lang.String getPlainName();

    protected abstract java.lang.String getWrappedName(java.lang.String containerName);

    @java.lang.Override
    public spoon.support.util.ImmutableMap addValueAs(spoon.support.util.ImmutableMap parameters, java.lang.Object value) {
        java.lang.Class<?> requiredType = getParameterValueType();
        if (((requiredType != null) && (value != null)) && ((requiredType.isInstance(value)) == false)) {
            return null;
        }
        if ((matches(value)) == false) {
            return null;
        }
        java.lang.Object newContainer = addValueToContainer(parameters, ( existingValue) -> {
            return merge(existingValue, value);
        });
        if (newContainer == (spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE)) {
            return null;
        }
        return ((spoon.support.util.ImmutableMap) (newContainer));
    }

    protected java.lang.Object addValueToContainer(java.lang.Object container, java.util.function.Function<java.lang.Object, java.lang.Object> merger) {
        if ((containerItemAccessor) != null) {
            return containerItemAccessor.addValueToContainer(container, ( existingValue) -> {
                return addValueAs(existingValue, merger);
            });
        }
        return addValueAs(container, merger);
    }

    protected java.lang.Object merge(java.lang.Object existingValue, java.lang.Object newValue) {
        spoon.reflect.meta.ContainerKind cc = getContainerKind(existingValue, newValue);
        spoon.pattern.internal.parameter.AbstractParameterInfo cca = getContainerKindAccessor(cc);
        if (cca == null) {
            return mergeSingle(existingValue, newValue);
        }
        return cca.addValueAs(existingValue, ( existingListItemValue) -> mergeSingle(existingListItemValue, newValue));
    }

    protected spoon.pattern.internal.parameter.AbstractParameterInfo getContainerKindAccessor(spoon.reflect.meta.ContainerKind containerKind) {
        switch (containerKind) {
            case SINGLE :
                return null;
            case LIST :
                return new spoon.pattern.internal.parameter.ListParameterInfo(this);
            case SET :
                return new spoon.pattern.internal.parameter.SetParameterInfo(this);
            case MAP :
                return new spoon.pattern.internal.parameter.MapParameterInfo(this);
        }
        throw new spoon.SpoonException(("Unexpected ContainerKind " + containerKind));
    }

    protected java.lang.Object mergeSingle(java.lang.Object existingValue, java.lang.Object newValue) {
        if ((newValue == null) && ((getMinOccurences()) > 0)) {
            return spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE;
        }
        if (existingValue != null) {
            if (existingValue.equals(newValue)) {
                return existingValue;
            }
            if ((newValue != null) && (existingValue.getClass().equals(newValue.getClass()))) {
                if (newValue instanceof spoon.reflect.reference.CtTypeReference) {
                    if (((spoon.reflect.reference.CtTypeReference<?>) (newValue)).getTypeErasure().equals(((spoon.reflect.reference.CtTypeReference<?>) (existingValue)).getTypeErasure())) {
                        return existingValue;
                    }
                }
            }
            spoon.Launcher.LOGGER.debug(((("incongruent match on parameter " + (getName())) + " with value ") + newValue));
            return spoon.pattern.internal.parameter.AbstractParameterInfo.NO_MERGE;
        }
        return newValue;
    }

    protected abstract java.lang.Object addValueAs(java.lang.Object container, java.util.function.Function<java.lang.Object, java.lang.Object> merger);

    protected <T> T castTo(java.lang.Object o, java.lang.Class<T> type) {
        if (o == null) {
            return getEmptyContainer();
        }
        if (type.isInstance(o)) {
            return ((T) (o));
        }
        throw new spoon.SpoonException(((("Cannot access parameter container of type " + (o.getClass())) + ". It expects ") + type));
    }

    protected abstract <T> T getEmptyContainer();

    public <T> spoon.pattern.internal.parameter.AbstractParameterInfo setMatchCondition(java.lang.Class<T> requiredType, java.util.function.Predicate<T> matchCondition) {
        this.parameterValueType = requiredType;
        this.matchCondition = ((java.util.function.Predicate) (matchCondition));
        return this;
    }

    protected boolean matches(java.lang.Object value) {
        if (((parameterValueType) != null) && ((value == null) || ((parameterValueType.isAssignableFrom(value.getClass())) == false))) {
            return false;
        }
        if ((matchCondition) == null) {
            return true;
        }
        return matchCondition.test(value);
    }

    public java.lang.Class<?> getParameterValueType() {
        return parameterValueType;
    }

    public spoon.pattern.internal.parameter.AbstractParameterInfo setParameterValueType(java.lang.Class<?> parameterValueType) {
        this.parameterValueType = parameterValueType;
        return this;
    }

    public boolean isMultiple() {
        return (getContainerKind(null, null)) != (spoon.reflect.meta.ContainerKind.SINGLE);
    }

    public spoon.pattern.internal.parameter.AbstractParameterInfo setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
        return this;
    }

    public int getMinOccurences() {
        return minOccurences;
    }

    public spoon.pattern.internal.parameter.AbstractParameterInfo setMinOccurences(int minOccurences) {
        this.minOccurences = minOccurences;
        return this;
    }

    public int getMaxOccurences() {
        return isMultiple() ? maxOccurences : java.lang.Math.min(maxOccurences, 1);
    }

    public void setMaxOccurences(int maxOccurences) {
        this.maxOccurences = maxOccurences;
    }

    public spoon.pattern.Quantifier getMatchingStrategy() {
        return matchingStrategy;
    }

    public void setMatchingStrategy(spoon.pattern.Quantifier matchingStrategy) {
        this.matchingStrategy = matchingStrategy;
    }

    public spoon.pattern.internal.ValueConvertor getValueConvertor() {
        if ((valueConvertor) != null) {
            return valueConvertor;
        }
        if ((containerItemAccessor) != null) {
            return containerItemAccessor.getValueConvertor();
        }
        throw new spoon.SpoonException("ValueConvertor is not defined.");
    }

    public spoon.pattern.internal.parameter.AbstractParameterInfo setValueConvertor(spoon.pattern.internal.ValueConvertor valueConvertor) {
        if (valueConvertor == null) {
            throw new spoon.SpoonException("valueConvertor must not be null");
        }
        this.valueConvertor = valueConvertor;
        return this;
    }

    public boolean isRepeatable() {
        if ((repeatable) != null) {
            return repeatable;
        }
        return isMultiple();
    }

    public boolean isMandatory(spoon.support.util.ImmutableMap parameters) {
        int nrOfValues = getNumberOfValues(parameters);
        return nrOfValues < (getMinOccurences());
    }

    public boolean isTryNextMatch(spoon.support.util.ImmutableMap parameters) {
        int nrOfValues = getNumberOfValues(parameters);
        if ((getContainerKind(parameters)) == (spoon.reflect.meta.ContainerKind.SINGLE)) {
            return true;
        }
        return nrOfValues < (getMaxOccurences());
    }

    private int getNumberOfValues(spoon.support.util.ImmutableMap parameters) {
        if ((parameters.hasValue(getName())) == false) {
            return 0;
        }
        java.lang.Object value = parameters.getValue(getName());
        if (value instanceof java.util.Collection) {
            return ((java.util.Collection) (value)).size();
        }
        return 1;
    }

    public spoon.reflect.meta.ContainerKind getContainerKind() {
        return containerKind;
    }

    public spoon.pattern.internal.parameter.AbstractParameterInfo setContainerKind(spoon.reflect.meta.ContainerKind containerKind) {
        this.containerKind = containerKind;
        return this;
    }

    protected spoon.reflect.meta.ContainerKind getContainerKind(spoon.support.util.ImmutableMap params) {
        return getContainerKind(params.getValue(getName()), null);
    }

    protected spoon.reflect.meta.ContainerKind getContainerKind(java.lang.Object existingValue, java.lang.Object value) {
        if ((containerKind) != null) {
            return containerKind;
        }
        if (existingValue instanceof java.util.List) {
            return spoon.reflect.meta.ContainerKind.LIST;
        }
        if (existingValue instanceof java.util.Set) {
            return spoon.reflect.meta.ContainerKind.SET;
        }
        if (existingValue instanceof java.util.Map) {
            return spoon.reflect.meta.ContainerKind.MAP;
        }
        if (existingValue instanceof spoon.support.util.ImmutableMap) {
            return spoon.reflect.meta.ContainerKind.MAP;
        }
        if (existingValue != null) {
            return spoon.reflect.meta.ContainerKind.SINGLE;
        }
        if (value instanceof java.util.List) {
            return spoon.reflect.meta.ContainerKind.LIST;
        }
        if (value instanceof java.util.Set) {
            return spoon.reflect.meta.ContainerKind.SET;
        }
        if (value instanceof java.util.Map.Entry<?, ?>) {
            return spoon.reflect.meta.ContainerKind.MAP;
        }
        if (value instanceof java.util.Map) {
            return spoon.reflect.meta.ContainerKind.MAP;
        }
        if (value instanceof spoon.support.util.ImmutableMap) {
            return spoon.reflect.meta.ContainerKind.MAP;
        }
        return spoon.reflect.meta.ContainerKind.SINGLE;
    }

    @java.lang.Override
    public <T> void getValueAs(spoon.reflect.factory.Factory factory, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        java.lang.Object rawValue = getValue(parameters);
        if ((isMultiple()) && (rawValue instanceof spoon.reflect.code.CtBlock<?>)) {
            rawValue = ((spoon.reflect.code.CtBlock<?>) (rawValue)).getStatements();
        }
        convertValue(factory, result, rawValue);
    }

    protected java.lang.Object getValue(spoon.support.util.ImmutableMap parameters) {
        if ((containerItemAccessor) != null) {
            return containerItemAccessor.getValue(parameters);
        }
        return parameters;
    }

    protected <T> void convertValue(spoon.reflect.factory.Factory factory, spoon.pattern.internal.ResultHolder<T> result, java.lang.Object rawValue) {
        if (result.isMultiple()) {
            spoon.pattern.internal.parameter.AbstractParameterInfo.forEachItem(rawValue, ( singleValue) -> {
                T convertedValue = convertSingleValue(factory, singleValue, result.getRequiredClass());
                if (convertedValue != null) {
                    result.addResult(convertedValue);
                }
            });
        }else {
            result.addResult(convertSingleValue(factory, rawValue, result.getRequiredClass()));
        }
    }

    protected <T> T convertSingleValue(spoon.reflect.factory.Factory factory, java.lang.Object value, java.lang.Class<T> type) {
        spoon.pattern.internal.ValueConvertor valueConvertor = getValueConvertor();
        return ((T) (valueConvertor.getValueAs(factory, value, type)));
    }

    @java.lang.SuppressWarnings("unchecked")
    static void forEachItem(java.lang.Object multipleValues, java.util.function.Consumer<java.lang.Object> consumer) {
        if (multipleValues instanceof spoon.reflect.code.CtStatementList) {
            consumer.accept(multipleValues);
            return;
        }
        if (multipleValues instanceof java.lang.Iterable) {
            for (java.lang.Object item : ((java.lang.Iterable<java.lang.Object>) (multipleValues))) {
                consumer.accept(item);
            }
            return;
        }
        if (multipleValues instanceof java.lang.Object[]) {
            for (java.lang.Object item : ((java.lang.Object[]) (multipleValues))) {
                consumer.accept(item);
            }
            return;
        }
        consumer.accept(multipleValues);
    }

    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(getName());
        if ((getParameterValueType()) != null) {
            sb.append(" : ");
            sb.append(getParameterValueType().getName());
        }
        return sb.toString();
    }
}

