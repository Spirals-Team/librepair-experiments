package spoon.reflect;


public class CtModelImpl implements spoon.reflect.CtModel {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public <R extends spoon.reflect.declaration.CtElement> spoon.reflect.visitor.chain.CtQuery filterChildren(spoon.reflect.visitor.Filter<R> filter) {
        return getUnnamedModule().getFactory().Query().createQuery(this.getAllModules().toArray()).filterChildren(filter);
    }

    @java.lang.Override
    public <I, R> spoon.reflect.visitor.chain.CtQuery map(spoon.reflect.visitor.chain.CtFunction<I, R> function) {
        return getUnnamedModule().getFactory().Query().createQuery(this.getAllModules().toArray()).map(function);
    }

    @java.lang.Override
    public <I> spoon.reflect.visitor.chain.CtQuery map(spoon.reflect.visitor.chain.CtConsumableFunction<I> queryStep) {
        return getUnnamedModule().getFactory().Query().createQuery(this.getAllModules().toArray()).map(queryStep);
    }

    public static class CtRootPackage extends spoon.support.reflect.declaration.CtPackageImpl {
        {
            this.setSimpleName(spoon.reflect.declaration.CtPackage.TOP_LEVEL_PACKAGE_NAME);
        }

        @java.lang.Override
        public <T extends spoon.reflect.declaration.CtNamedElement> T setSimpleName(java.lang.String name) {
            if (name == null) {
                return ((T) (this));
            }
            if (name.equals(spoon.reflect.declaration.CtPackage.TOP_LEVEL_PACKAGE_NAME)) {
                return super.setSimpleName(name);
            }
            return ((T) (this));
        }

        @java.lang.Override
        public java.lang.String getQualifiedName() {
            return "";
        }

        @java.lang.Override
        public java.lang.String toString() {
            return spoon.reflect.declaration.CtPackage.TOP_LEVEL_PACKAGE_NAME;
        }
    }

    private final spoon.reflect.declaration.CtModule unnamedModule;

    public CtModelImpl(spoon.reflect.factory.Factory f) {
        this.unnamedModule = new spoon.reflect.factory.ModuleFactory.CtUnnamedModule();
        this.unnamedModule.setFactory(f);
        this.unnamedModule.setRootPackage(new spoon.reflect.CtModelImpl.CtRootPackage());
        getRootPackage().setFactory(f);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtPackage getRootPackage() {
        return getUnnamedModule().getRootPackage();
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.declaration.CtType<?>> getAllTypes() {
        final java.util.List<spoon.reflect.declaration.CtType<?>> result = new java.util.ArrayList<>();
        getAllPackages().forEach(( ctPackage) -> {
            result.addAll(ctPackage.getTypes());
        });
        return result;
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.declaration.CtPackage> getAllPackages() {
        return java.util.Collections.unmodifiableCollection(getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtPackage.class)));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtModule getUnnamedModule() {
        return this.unnamedModule;
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.declaration.CtModule> getAllModules() {
        return ((spoon.reflect.factory.ModuleFactory.CtUnnamedModule) (this.unnamedModule)).getAllModules();
    }

    @java.lang.Override
    public void processWith(spoon.processing.Processor<?> processor) {
        spoon.support.QueueProcessingManager processingManager = new spoon.support.QueueProcessingManager(getUnnamedModule().getFactory());
        processingManager.addProcessor(processor);
        processingManager.process(getAllModules());
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtElement> java.util.List<E> getElements(spoon.reflect.visitor.Filter<E> filter) {
        return filterChildren(filter).list();
    }
}

