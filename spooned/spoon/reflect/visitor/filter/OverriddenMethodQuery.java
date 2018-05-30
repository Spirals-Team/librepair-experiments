package spoon.reflect.visitor.filter;


public class OverriddenMethodQuery implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtMethod<?>> {
    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtMethod<?> input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        spoon.reflect.declaration.CtPackage searchScope = input.getFactory().Package().getRootPackage();
        searchScope.filterChildren(new spoon.reflect.visitor.filter.OverriddenMethodFilter(input)).forEach(outputConsumer);
    }
}

