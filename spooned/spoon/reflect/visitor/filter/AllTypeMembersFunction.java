package spoon.reflect.visitor.filter;


public class AllTypeMembersFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtTypeInformation> , spoon.reflect.visitor.chain.CtQueryAware {
    private spoon.reflect.visitor.chain.CtQuery query;

    private final java.lang.Class<?> memberClass;

    private java.util.Set<java.lang.String> distinctSet;

    public AllTypeMembersFunction() {
        this.memberClass = null;
    }

    public AllTypeMembersFunction(java.lang.Class<?> memberClass) {
        this.memberClass = memberClass;
    }

    public spoon.reflect.visitor.filter.AllTypeMembersFunction distinctSet(java.util.Set<java.lang.String> distinctSet) {
        this.distinctSet = distinctSet;
        return this;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtTypeInformation input, final spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        final spoon.reflect.visitor.chain.CtQuery q = ((spoon.reflect.visitor.chain.CtQueryable) (input)).map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction(((distinctSet) == null ? new java.util.HashSet<java.lang.String>() : distinctSet)).includingSelf(true));
        q.forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtType<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtType<?> type) {
                for (spoon.reflect.declaration.CtTypeMember typeMember : type.getTypeMembers()) {
                    if (((memberClass) == null) || (memberClass.isInstance(typeMember))) {
                        outputConsumer.accept(typeMember);
                    }
                    if (query.isTerminated()) {
                        q.terminate();
                    }
                }
            }
        });
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }
}

