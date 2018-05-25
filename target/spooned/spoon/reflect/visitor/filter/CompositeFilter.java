package spoon.reflect.visitor.filter;


public class CompositeFilter<T extends spoon.reflect.declaration.CtElement> implements spoon.reflect.visitor.Filter<T> {
    public boolean matches(T element) {
        switch (operator) {
            case INTERSECTION :
                for (spoon.reflect.visitor.Filter<T> f : filters) {
                    if (!(hasMatch(f, element))) {
                        return false;
                    }
                }
                return true;
            case UNION :
                for (spoon.reflect.visitor.Filter<T> f : filters) {
                    if (hasMatch(f, element)) {
                        return true;
                    }
                }
                return false;
            case SUBSTRACTION :
                if ((filters.length) == 0) {
                    return false;
                }
                if (!(filters[0].matches(element))) {
                    return false;
                }
                for (int i = 1; i < (filters.length); i++) {
                    if (filters[i].matches(element)) {
                        return false;
                    }
                }
                return true;
            default :
                return false;
        }
    }

    spoon.reflect.visitor.Filter<T>[] filters;

    spoon.reflect.visitor.filter.FilteringOperator operator;

    public CompositeFilter(spoon.reflect.visitor.filter.FilteringOperator operator, spoon.reflect.visitor.Filter<T>... filters) {
        this.filters = filters;
        this.operator = operator;
    }

    private boolean hasMatch(spoon.reflect.visitor.Filter<T> filter, T element) {
        try {
            return filter.matches(element);
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    public java.lang.Class<T> getType() {
        return ((java.lang.Class<T>) (spoon.reflect.declaration.CtElement.class));
    }
}

