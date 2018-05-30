package spoon.reflect.visitor.filter;


public class ParentFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> , spoon.reflect.visitor.chain.CtQueryAware {
    private boolean includingSelf = false;

    private spoon.reflect.visitor.chain.CtQuery query;

    public ParentFunction() {
    }

    public spoon.reflect.visitor.filter.ParentFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtElement input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        if (input == null) {
            return;
        }
        if (includingSelf) {
            outputConsumer.accept(input);
        }
        spoon.reflect.declaration.CtElement parent = input;
        spoon.reflect.declaration.CtModule topLevel = input.getFactory().getModel().getUnnamedModule();
        while ((((parent != null) && (parent != topLevel)) && ((query.isTerminated()) == false)) && (parent.isParentInitialized())) {
            parent = parent.getParent();
            outputConsumer.accept(parent);
        } 
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }
}

