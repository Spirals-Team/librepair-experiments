package spoon.support.visitor.java.internal;


public class PackageRuntimeBuilderContext extends spoon.support.visitor.java.internal.AbstractRuntimeBuilderContext {
    private spoon.reflect.declaration.CtPackage ctPackage;

    public PackageRuntimeBuilderContext(spoon.reflect.declaration.CtPackage ctPackage) {
        super(ctPackage);
        this.ctPackage = ctPackage;
    }

    @java.lang.Override
    public void addType(spoon.reflect.declaration.CtType<?> aType) {
        ctPackage.addType(aType);
    }

    @java.lang.Override
    public void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        ctPackage.addAnnotation(ctAnnotation);
    }
}

