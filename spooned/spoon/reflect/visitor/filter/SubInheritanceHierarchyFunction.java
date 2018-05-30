package spoon.reflect.visitor.filter;


public class SubInheritanceHierarchyFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtTypeInformation> , spoon.reflect.visitor.chain.CtQueryAware {
    private boolean includingSelf = false;

    private boolean includingInterfaces = true;

    private spoon.reflect.visitor.chain.CtQuery query;

    private boolean failOnClassNotFound = false;

    public SubInheritanceHierarchyFunction() {
    }

    public spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    public spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction includingInterfaces(boolean includingInterfaces) {
        this.includingInterfaces = includingInterfaces;
        return this;
    }

    public spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction failOnClassNotFound(boolean failOnClassNotFound) {
        this.failOnClassNotFound = failOnClassNotFound;
        return this;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtTypeInformation input, final spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        final spoon.support.visitor.SubInheritanceHierarchyResolver fnc = new spoon.support.visitor.SubInheritanceHierarchyResolver(((spoon.reflect.declaration.CtElement) (input)).getFactory().getModel().getRootPackage()).failOnClassNotFound(failOnClassNotFound).includingInterfaces(includingInterfaces);
        if (includingSelf) {
            if (input instanceof spoon.reflect.reference.CtTypeReference) {
                outputConsumer.accept(((spoon.reflect.reference.CtTypeReference<?>) (input)).getTypeDeclaration());
            }else {
                outputConsumer.accept(((spoon.reflect.declaration.CtType<?>) (input)));
            }
        }
        fnc.addSuperType(input);
        fnc.forEachSubTypeInPackage(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtType>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtType typeInfo) {
                outputConsumer.accept(typeInfo);
                if (query.isTerminated()) {
                }
            }
        });
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }
}

