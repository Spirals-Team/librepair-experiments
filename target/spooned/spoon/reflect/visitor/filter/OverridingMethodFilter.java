package spoon.reflect.visitor.filter;


public class OverridingMethodFilter implements spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtMethod<?>> {
    private final spoon.reflect.declaration.CtMethod<?> method;

    private boolean includingSelf = false;

    public OverridingMethodFilter(spoon.reflect.declaration.CtMethod<?> method) {
        this.method = method;
    }

    public spoon.reflect.visitor.filter.OverridingMethodFilter includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.declaration.CtMethod<?> element) {
        if ((method) == element) {
            return this.includingSelf;
        }
        return element.isOverriding(method);
    }
}

