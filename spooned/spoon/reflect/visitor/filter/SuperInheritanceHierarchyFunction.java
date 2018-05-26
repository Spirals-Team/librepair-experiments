package spoon.reflect.visitor.filter;


public class SuperInheritanceHierarchyFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtTypeInformation> , spoon.reflect.visitor.chain.CtQueryAware {
    private boolean includingSelf = false;

    private boolean includingInterfaces = true;

    private spoon.reflect.visitor.chain.CtQuery query;

    private boolean failOnClassNotFound = false;

    private spoon.reflect.visitor.chain.CtScannerListener listener;

    private boolean returnTypeReferences = false;

    private static class Listener implements spoon.reflect.visitor.chain.CtScannerListener {
        public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.reference.CtTypeReference<?> typeRef, boolean isClass) {
            return enter(((spoon.reflect.declaration.CtElement) (typeRef)));
        }

        public void exit(spoon.reflect.reference.CtTypeReference<?> typeRef, boolean isClass) {
            exit(((spoon.reflect.declaration.CtElement) (typeRef)));
        }

        @java.lang.Override
        public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
            return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
        }

        @java.lang.Override
        public void exit(spoon.reflect.declaration.CtElement element) {
        }
    }

    public SuperInheritanceHierarchyFunction() {
    }

    public SuperInheritanceHierarchyFunction(java.util.Set<java.lang.String> visitedSet) {
        listener = new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.DistinctTypeListener(visitedSet);
    }

    public static class DistinctTypeListener extends spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener {
        java.util.Set<java.lang.String> visitedSet;

        public DistinctTypeListener(java.util.Set<java.lang.String> visitedSet) {
            this.visitedSet = visitedSet;
        }

        @java.lang.Override
        public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
            if (visitedSet.add(((spoon.reflect.declaration.CtTypeInformation) (element)).getQualifiedName())) {
                return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
            }
            return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
        }

        @java.lang.Override
        public void exit(spoon.reflect.declaration.CtElement element) {
        }
    }

    public spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    public spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction includingInterfaces(boolean includingInterfaces) {
        this.includingInterfaces = includingInterfaces;
        return this;
    }

    public spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction returnTypeReferences(boolean returnTypeReferences) {
        this.returnTypeReferences = returnTypeReferences;
        return this;
    }

    public spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction setListener(spoon.reflect.visitor.chain.CtScannerListener listener) {
        if ((this.listener) != null) {
            throw new spoon.SpoonException("Cannot register listener on instance created with constructor which accepts the Set<String>. Use the no parameter constructor if listener has to be registered");
        }
        this.listener = listener;
        return this;
    }

    public spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction failOnClassNotFound(boolean failOnClassNotFound) {
        this.failOnClassNotFound = failOnClassNotFound;
        return this;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtTypeInformation input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        spoon.reflect.reference.CtTypeReference<?> typeRef;
        spoon.reflect.declaration.CtType<?> type;
        boolean isClass;
        if (input instanceof spoon.reflect.declaration.CtType) {
            type = ((spoon.reflect.declaration.CtType<?>) (input));
            typeRef = type.getReference();
        }else {
            typeRef = ((spoon.reflect.reference.CtTypeReference<?>) (input));
            try {
                type = typeRef.getTypeDeclaration();
            } catch (spoon.support.SpoonClassNotFoundException e) {
                if ((typeRef.getFactory().getEnvironment().getNoClasspath()) == false) {
                    throw e;
                }
                type = null;
            }
        }
        isClass = (type == null) ? false : type instanceof spoon.reflect.declaration.CtClass;
        if ((isClass == false) && ((includingInterfaces) == false)) {
            return;
        }
        spoon.reflect.visitor.chain.ScanningMode mode = enter(typeRef, isClass);
        if (mode == (spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL)) {
            return;
        }
        if (includingSelf) {
            sendResult(typeRef, outputConsumer);
            if (query.isTerminated()) {
                mode = spoon.reflect.visitor.chain.ScanningMode.SKIP_CHILDREN;
            }
        }
        if (mode == (spoon.reflect.visitor.chain.ScanningMode.NORMAL)) {
            if (isClass == false) {
                visitSuperInterfaces(typeRef, outputConsumer);
            }else {
                visitSuperClasses(typeRef, outputConsumer, includingInterfaces);
            }
        }
        exit(typeRef, isClass);
    }

    protected void visitSuperClasses(spoon.reflect.reference.CtTypeReference<?> superTypeRef, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer, boolean includingInterfaces) {
        if (java.lang.Object.class.getName().equals(superTypeRef.getQualifiedName())) {
            return;
        }
        if (includingInterfaces) {
            visitSuperInterfaces(superTypeRef, outputConsumer);
            if (query.isTerminated()) {
                return;
            }
        }
        spoon.reflect.reference.CtTypeReference<?> superClassRef = superTypeRef.getSuperclass();
        if (superClassRef == null) {
            superClassRef = superTypeRef.getFactory().Type().OBJECT;
        }
        spoon.reflect.visitor.chain.ScanningMode mode = enter(superClassRef, true);
        if (mode == (spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL)) {
            return;
        }
        sendResult(superClassRef, outputConsumer);
        if ((mode == (spoon.reflect.visitor.chain.ScanningMode.NORMAL)) && ((query.isTerminated()) == false)) {
            visitSuperClasses(superClassRef, outputConsumer, includingInterfaces);
        }
        exit(superClassRef, true);
    }

    protected void visitSuperInterfaces(spoon.reflect.reference.CtTypeReference<?> type, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        java.util.Set<spoon.reflect.reference.CtTypeReference<?>> superInterfaces;
        try {
            superInterfaces = type.getSuperInterfaces();
        } catch (spoon.support.SpoonClassNotFoundException e) {
            if (failOnClassNotFound) {
                throw e;
            }
            spoon.Launcher.LOGGER.warn(((("Cannot load class: " + (type.getQualifiedName())) + " with class loader ") + (java.lang.Thread.currentThread().getContextClassLoader())));
            return;
        }
        for (spoon.reflect.reference.CtTypeReference<?> ifaceRef : superInterfaces) {
            spoon.reflect.visitor.chain.ScanningMode mode = enter(ifaceRef, false);
            if (mode == (spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL)) {
                continue;
            }
            sendResult(ifaceRef, outputConsumer);
            if ((mode == (spoon.reflect.visitor.chain.ScanningMode.NORMAL)) && ((query.isTerminated()) == false)) {
                visitSuperInterfaces(ifaceRef, outputConsumer);
            }
            exit(ifaceRef, false);
            if (query.isTerminated()) {
                return;
            }
        }
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }

    private spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.reference.CtTypeReference<?> type, boolean isClass) {
        if ((listener) == null) {
            return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
        }
        if ((listener) instanceof spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener) {
            spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener typeListener = ((spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener) (listener));
            return typeListener.enter(type, isClass);
        }
        return listener.enter(type);
    }

    private void exit(spoon.reflect.reference.CtTypeReference<?> type, boolean isClass) {
        if ((listener) != null) {
            if ((listener) instanceof spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener) {
                ((spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener) (listener)).exit(type, isClass);
            }else {
                listener.exit(type);
            }
        }
    }

    protected void sendResult(spoon.reflect.reference.CtTypeReference<?> typeRef, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        if (returnTypeReferences) {
            outputConsumer.accept(typeRef);
        }else {
            spoon.reflect.declaration.CtType<?> type;
            try {
                type = typeRef.getTypeDeclaration();
            } catch (spoon.support.SpoonClassNotFoundException e) {
                if (failOnClassNotFound) {
                    throw e;
                }
                spoon.Launcher.LOGGER.warn(((("Cannot load class: " + (typeRef.getQualifiedName())) + " with class loader ") + (java.lang.Thread.currentThread().getContextClassLoader())));
                return;
            }
            outputConsumer.accept(type);
        }
    }
}

