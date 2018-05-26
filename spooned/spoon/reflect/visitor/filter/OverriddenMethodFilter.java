package spoon.reflect.visitor.filter;


public class OverriddenMethodFilter implements spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtMethod<?>> {
    private final spoon.reflect.declaration.CtMethod<?> method;

    private final spoon.support.visitor.ClassTypingContext context;

    private boolean includingSelf = false;

    public OverriddenMethodFilter(spoon.reflect.declaration.CtMethod<?> method) {
        this.method = method;
        context = new spoon.support.visitor.ClassTypingContext(method.getDeclaringType());
    }

    public spoon.reflect.visitor.filter.OverriddenMethodFilter includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.declaration.CtMethod<?> element) {
        if ((method) == element) {
            return this.includingSelf;
        }
        return context.isOverriding(method, element);
    }
}

