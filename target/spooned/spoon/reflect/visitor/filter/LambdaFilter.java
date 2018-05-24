package spoon.reflect.visitor.filter;


public class LambdaFilter implements spoon.reflect.visitor.Filter<spoon.reflect.code.CtLambda<?>> {
    private java.util.Set<java.lang.String> qualifiedNamesOfInterfaces = new java.util.HashSet<>();

    public LambdaFilter() {
    }

    public LambdaFilter(spoon.reflect.declaration.CtInterface<?> iface) {
        addImplementingInterface(iface);
    }

    public LambdaFilter(spoon.reflect.reference.CtTypeReference<?> iface) {
        addImplementingInterface(iface);
    }

    public spoon.reflect.visitor.filter.LambdaFilter addImplementingInterface(spoon.reflect.declaration.CtTypeInformation typeInfo) {
        if (typeInfo instanceof spoon.reflect.declaration.CtType) {
            if (typeInfo instanceof spoon.reflect.declaration.CtInterface) {
                qualifiedNamesOfInterfaces.add(typeInfo.getQualifiedName());
            }
        }else {
            qualifiedNamesOfInterfaces.add(typeInfo.getQualifiedName());
        }
        return this;
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.code.CtLambda<?> lambda) {
        return qualifiedNamesOfInterfaces.contains(lambda.getType().getQualifiedName());
    }
}

