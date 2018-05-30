package spoon.reflect.visitor.filter;


public class ExecutableReferenceFilter implements spoon.reflect.visitor.Filter<spoon.reflect.reference.CtExecutableReference<?>> {
    private java.util.Map<spoon.reflect.declaration.CtExecutable<?>, spoon.reflect.declaration.CtExecutable<?>> executables = new java.util.IdentityHashMap<>();

    private java.util.Set<java.lang.String> typeQualifiedNames = new java.util.HashSet<>();

    private java.util.Set<java.lang.String> methodNames = new java.util.HashSet<>();

    public ExecutableReferenceFilter() {
    }

    public ExecutableReferenceFilter(spoon.reflect.declaration.CtExecutable<?> executable) {
        addExecutable(executable);
    }

    public spoon.reflect.visitor.filter.ExecutableReferenceFilter addExecutable(spoon.reflect.declaration.CtExecutable<?> executable) {
        executables.put(executable, executable);
        if (executable instanceof spoon.reflect.declaration.CtTypeMember) {
            spoon.reflect.declaration.CtType<?> declType = ((spoon.reflect.declaration.CtTypeMember) (executable)).getDeclaringType();
            if (declType == null) {
                throw new spoon.SpoonException("Cannot search for executable reference, which has no declaring type");
            }
            typeQualifiedNames.add(declType.getQualifiedName());
            if (executable instanceof spoon.reflect.declaration.CtMethod<?>) {
                methodNames.add(((spoon.reflect.declaration.CtMethod<?>) (executable)).getSimpleName());
            }
        }
        return this;
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.reference.CtExecutableReference<?> execRef) {
        if (execRef.getSimpleName().startsWith(spoon.reflect.reference.CtExecutableReference.LAMBDA_NAME_PREFIX)) {
            return executables.containsKey(execRef.getDeclaration());
        }else {
            if (typeQualifiedNames.contains(execRef.getDeclaringType().getQualifiedName())) {
                if ((spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME.equals(execRef.getSimpleName())) || (methodNames.contains(execRef.getSimpleName()))) {
                    return executables.containsKey(execRef.getDeclaration());
                }
            }
        }
        return false;
    }
}

