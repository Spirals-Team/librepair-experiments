package spoon.support.visitor.java.internal;


public class TypeRuntimeBuilderContext extends spoon.support.visitor.java.internal.AbstractRuntimeBuilderContext {
    protected spoon.reflect.declaration.CtType type;

    protected java.lang.reflect.Type rtType;

    private java.util.Map<java.lang.String, spoon.reflect.declaration.CtTypeParameter> mapTypeParameters;

    public TypeRuntimeBuilderContext(java.lang.reflect.Type rtType, spoon.reflect.declaration.CtType type) {
        super(type);
        this.type = type;
        this.rtType = rtType;
        this.mapTypeParameters = new java.util.HashMap<>();
    }

    @java.lang.Override
    public void addPackage(spoon.reflect.declaration.CtPackage ctPackage) {
        ctPackage.addType(type);
    }

    @java.lang.Override
    public void addType(spoon.reflect.declaration.CtType<?> aType) {
        type.addNestedType(aType);
    }

    @java.lang.Override
    public void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        type.addAnnotation(ctAnnotation);
    }

    @java.lang.Override
    public void addMethod(spoon.reflect.declaration.CtMethod<?> ctMethod) {
        type.addMethod(ctMethod);
    }

    @java.lang.Override
    public void addField(spoon.reflect.declaration.CtField<?> ctField) {
        type.addField(ctField);
    }

    @java.lang.Override
    public void addFormalType(spoon.reflect.declaration.CtTypeParameter parameterRef) {
        this.type.addFormalCtTypeParameter(parameterRef);
        this.mapTypeParameters.put(parameterRef.getSimpleName(), parameterRef);
    }

    @java.lang.Override
    public void addTypeReference(spoon.reflect.path.CtRole role, spoon.reflect.reference.CtTypeReference<?> typeReference) {
        switch (role) {
            case INTERFACE :
                type.addSuperInterface(typeReference);
                return;
            case SUPER_TYPE :
                if ((type) instanceof spoon.reflect.declaration.CtTypeParameter) {
                    ((spoon.reflect.declaration.CtTypeParameter) (this.type)).setSuperclass(typeReference);
                    return;
                }
        }
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtTypeParameter getTypeParameter(java.lang.reflect.GenericDeclaration genericDeclaration, java.lang.String string) {
        return (rtType) == genericDeclaration ? this.mapTypeParameters.get(string) : null;
    }
}

