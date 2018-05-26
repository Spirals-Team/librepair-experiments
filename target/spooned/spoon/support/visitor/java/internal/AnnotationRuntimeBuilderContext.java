package spoon.support.visitor.java.internal;


public class AnnotationRuntimeBuilderContext extends spoon.support.visitor.java.internal.AbstractRuntimeBuilderContext {
    private spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation;

    public AnnotationRuntimeBuilderContext(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        super(ctAnnotation);
        this.ctAnnotation = ctAnnotation;
    }

    @java.lang.Override
    public void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        this.ctAnnotation.addAnnotation(ctAnnotation);
    }

    @java.lang.Override
    public void addTypeReference(spoon.reflect.path.CtRole role, spoon.reflect.reference.CtTypeReference<?> typeReference) {
        switch (role) {
            case ANNOTATION_TYPE :
                ctAnnotation.setAnnotationType(((spoon.reflect.reference.CtTypeReference<? extends java.lang.annotation.Annotation>) (typeReference)));
                ctAnnotation.setType(((spoon.reflect.reference.CtTypeReference<java.lang.annotation.Annotation>) (typeReference)));
                return;
        }
        super.addTypeReference(role, typeReference);
    }

    public spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> getCtAnnotation() {
        return this.ctAnnotation;
    }
}

