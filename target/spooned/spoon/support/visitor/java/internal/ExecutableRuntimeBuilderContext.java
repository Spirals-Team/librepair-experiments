package spoon.support.visitor.java.internal;


public class ExecutableRuntimeBuilderContext extends spoon.support.visitor.java.internal.AbstractRuntimeBuilderContext {
    private spoon.reflect.declaration.CtExecutable<?> ctExecutable;

    private java.lang.reflect.Executable executable;

    private java.util.Map<java.lang.String, spoon.reflect.declaration.CtTypeParameter> mapTypeParameters;

    public ExecutableRuntimeBuilderContext(java.lang.reflect.Executable executable, spoon.reflect.declaration.CtMethod<?> ctMethod) {
        super(ctMethod);
        this.ctExecutable = ctMethod;
        this.executable = executable;
        this.mapTypeParameters = new java.util.HashMap<>();
    }

    public ExecutableRuntimeBuilderContext(java.lang.reflect.Executable executable, spoon.reflect.declaration.CtConstructor<?> ctConstructor) {
        super(ctConstructor);
        this.ctExecutable = ctConstructor;
        this.executable = executable;
        this.mapTypeParameters = new java.util.HashMap<>();
    }

    @java.lang.Override
    public void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        ctExecutable.addAnnotation(ctAnnotation);
    }

    @java.lang.Override
    public void addParameter(spoon.reflect.declaration.CtParameter ctParameter) {
        ctExecutable.addParameter(ctParameter);
    }

    @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
    @java.lang.Override
    public void addTypeReference(spoon.reflect.path.CtRole role, spoon.reflect.reference.CtTypeReference<?> typeReference) {
        switch (role) {
            case THROWN :
                ctExecutable.addThrownType(((spoon.reflect.reference.CtTypeReference) (typeReference)));
                return;
            case TYPE :
                ctExecutable.setType(((spoon.reflect.reference.CtTypeReference) (typeReference)));
                return;
        }
        super.addTypeReference(role, typeReference);
    }

    @java.lang.Override
    public void addFormalType(spoon.reflect.declaration.CtTypeParameter parameterRef) {
        if ((ctExecutable) instanceof spoon.reflect.declaration.CtFormalTypeDeclarer) {
            ((spoon.reflect.declaration.CtFormalTypeDeclarer) (ctExecutable)).addFormalCtTypeParameter(parameterRef);
            this.mapTypeParameters.put(parameterRef.getSimpleName(), parameterRef);
            return;
        }
        super.addFormalType(parameterRef);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtTypeParameter getTypeParameter(java.lang.reflect.GenericDeclaration genericDeclaration, java.lang.String string) {
        return (executable) == genericDeclaration ? this.mapTypeParameters.get(string) : null;
    }
}

