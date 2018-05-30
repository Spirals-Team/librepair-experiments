package spoon.support.visitor;


public class SubInheritanceHierarchyResolver {
    private spoon.reflect.declaration.CtPackage inputPackage;

    private boolean includingInterfaces = true;

    private java.util.Set<java.lang.String> targetSuperTypes = new java.util.HashSet<>();

    private boolean hasSuperInterface = false;

    private boolean failOnClassNotFound = false;

    public SubInheritanceHierarchyResolver(spoon.reflect.declaration.CtPackage input) {
        inputPackage = input;
    }

    public spoon.support.visitor.SubInheritanceHierarchyResolver addSuperType(spoon.reflect.declaration.CtTypeInformation superType) {
        targetSuperTypes.add(superType.getQualifiedName());
        if ((hasSuperInterface) == false) {
            hasSuperInterface = superType.isInterface();
        }
        return this;
    }

    public spoon.support.visitor.SubInheritanceHierarchyResolver includingInterfaces(boolean includingInterfaces) {
        this.includingInterfaces = includingInterfaces;
        return this;
    }

    public spoon.support.visitor.SubInheritanceHierarchyResolver failOnClassNotFound(boolean failOnClassNotFound) {
        this.failOnClassNotFound = failOnClassNotFound;
        return this;
    }

    public <T extends spoon.reflect.declaration.CtType<?>> void forEachSubTypeInPackage(final spoon.reflect.visitor.chain.CtConsumer<T> outputConsumer) {
        final java.util.Set<java.lang.String> allVisitedTypeNames = new java.util.HashSet<>();
        final java.util.Deque<spoon.reflect.reference.CtTypeReference<?>> currentSubTypes = new java.util.ArrayDeque<>();
        final spoon.reflect.visitor.chain.CtQuery q = inputPackage.map(new spoon.reflect.visitor.filter.CtScannerFunction());
        if (includingInterfaces) {
            q.select(spoon.support.visitor.SubInheritanceHierarchyResolver.typeFilter);
        }else {
            q.select(spoon.support.visitor.SubInheritanceHierarchyResolver.classFilter);
        }
        q.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingInterfaces(hasSuperInterface).failOnClassNotFound(failOnClassNotFound).setListener(new spoon.reflect.visitor.chain.CtScannerListener() {
            @java.lang.Override
            public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
                final spoon.reflect.reference.CtTypeReference<?> typeRef = ((spoon.reflect.reference.CtTypeReference<?>) (element));
                java.lang.String qName = typeRef.getQualifiedName();
                if (targetSuperTypes.contains(qName)) {
                    while ((currentSubTypes.size()) > 0) {
                        final spoon.reflect.reference.CtTypeReference<?> currentTypeRef = currentSubTypes.pop();
                        java.lang.String currentQName = currentTypeRef.getQualifiedName();
                        if (!(targetSuperTypes.contains(currentQName))) {
                            targetSuperTypes.add(currentQName);
                            outputConsumer.accept(((T) (currentTypeRef.getTypeDeclaration())));
                        }
                    } 
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                if ((allVisitedTypeNames.add(qName)) == false) {
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                currentSubTypes.push(typeRef);
                return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement element) {
                spoon.reflect.declaration.CtTypeInformation type = ((spoon.reflect.declaration.CtTypeInformation) (element));
                if ((currentSubTypes.isEmpty()) == false) {
                    spoon.reflect.declaration.CtTypeInformation stackType = currentSubTypes.pop();
                    if (stackType != type) {
                        throw new spoon.SpoonException("CtScannerListener#exit was not called after enter.");
                    }
                }
            }
        })).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtType<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtType<?> type) {
            }
        });
    }

    private static final spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>> typeFilter = new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>>() {
        @java.lang.Override
        public boolean matches(spoon.reflect.declaration.CtType<?> type) {
            if (type instanceof spoon.reflect.declaration.CtTypeParameter) {
                return false;
            }
            return true;
        }
    };

    private static final spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtClass<?>> classFilter = new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtClass<?>>(spoon.reflect.declaration.CtClass.class);
}

