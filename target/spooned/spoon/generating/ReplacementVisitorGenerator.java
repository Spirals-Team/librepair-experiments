package spoon.generating;


public class ReplacementVisitorGenerator extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtType<?>> {
    @java.lang.Override
    public boolean isToBeProcessed(spoon.reflect.declaration.CtType<?> candidate) {
        return (spoon.reflect.visitor.CtScanner.class.getName().equals(candidate.getQualifiedName())) && (super.isToBeProcessed(candidate));
    }

    @java.lang.Override
    public void process(spoon.reflect.declaration.CtType<?> element) {
        new spoon.generating.replace.ReplaceScanner(createReplacementVisitor()).scan(getFactory().Class().get(spoon.reflect.visitor.CtScanner.class));
    }

    private spoon.reflect.declaration.CtClass<java.lang.Object> createReplacementVisitor() {
        final spoon.reflect.declaration.CtPackage aPackage = getFactory().Package().getOrCreate(spoon.generating.replace.ReplaceScanner.TARGET_REPLACE_PACKAGE);
        final spoon.reflect.declaration.CtClass<java.lang.Object> target = getFactory().Class().get(spoon.generating.replace.ReplaceScanner.GENERATING_REPLACE_VISITOR);
        target.delete();
        target.addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        aPackage.addType(target);
        final java.util.List<spoon.reflect.reference.CtTypeReference> references = target.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtTypeReference>(spoon.reflect.reference.CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeReference reference) {
                return spoon.generating.replace.ReplaceScanner.GENERATING_REPLACE_VISITOR.equals(reference.getQualifiedName());
            }
        });
        for (spoon.reflect.reference.CtTypeReference reference : references) {
            reference.setPackage(aPackage.getReference());
        }
        return target;
    }
}

