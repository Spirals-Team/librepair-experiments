package spoon.reflect.visitor.filter;


public class InvocationFilter implements spoon.reflect.visitor.Filter<spoon.reflect.code.CtInvocation<?>> {
    private spoon.reflect.reference.CtExecutableReference<?> executable;

    public InvocationFilter(spoon.reflect.reference.CtExecutableReference<?> executable) {
        this.executable = executable;
    }

    public InvocationFilter(spoon.reflect.declaration.CtMethod<?> method) {
        this(method.getReference());
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.code.CtInvocation<?> invocation) {
        return invocation.getExecutable().isOverriding(executable);
    }
}

