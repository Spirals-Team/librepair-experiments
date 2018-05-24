package spoon.reflect.visitor.filter;


public abstract class AbstractFilter<T extends spoon.reflect.declaration.CtElement> implements spoon.reflect.visitor.Filter<T> {
    private java.lang.Class<T> type;

    @java.lang.SuppressWarnings("unchecked")
    public AbstractFilter(java.lang.Class<? super T> type) {
        this.type = ((java.lang.Class<T>) (type));
    }

    @java.lang.SuppressWarnings("unchecked")
    public AbstractFilter() {
        java.lang.reflect.Method method = spoon.support.util.RtHelper.getMethod(getClass(), "matches", 1);
        if (method == null) {
            throw new spoon.SpoonException(("The method matches with one parameter was not found on the class " + (getClass().getName())));
        }
        this.type = ((java.lang.Class<T>) (method.getParameterTypes()[0]));
    }

    public java.lang.Class<T> getType() {
        return type;
    }

    @java.lang.Override
    public boolean matches(T element) {
        return type.isAssignableFrom(element.getClass());
    }
}

