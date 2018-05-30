package spoon.reflect.visitor.filter;


public class SiblingsFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> {
    public enum Mode {
        ALL, PREVIOUS, NEXT;}

    private spoon.reflect.visitor.filter.SiblingsFunction.Mode mode = spoon.reflect.visitor.filter.SiblingsFunction.Mode.ALL;

    private boolean includingSelf = false;

    public SiblingsFunction() {
    }

    public spoon.reflect.visitor.filter.SiblingsFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    public spoon.reflect.visitor.filter.SiblingsFunction mode(spoon.reflect.visitor.filter.SiblingsFunction.Mode mode) {
        this.mode = mode;
        return this;
    }

    @java.lang.Override
    public void apply(final spoon.reflect.declaration.CtElement input, final spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        final spoon.reflect.declaration.CtElement parent = input.getParent();
        parent.accept(new spoon.reflect.visitor.CtScanner() {
            boolean hasVisitedInput = false;

            boolean visitPrev = ((mode) == (spoon.reflect.visitor.filter.SiblingsFunction.Mode.ALL)) || ((mode) == (spoon.reflect.visitor.filter.SiblingsFunction.Mode.PREVIOUS));

            boolean visitNext = ((mode) == (spoon.reflect.visitor.filter.SiblingsFunction.Mode.ALL)) || ((mode) == (spoon.reflect.visitor.filter.SiblingsFunction.Mode.NEXT));

            @java.lang.Override
            public void scan(spoon.reflect.declaration.CtElement element) {
                if (((element != null) && (element.isParentInitialized())) && ((element.getParent()) == parent)) {
                    boolean canVisit = (hasVisitedInput) ? visitNext : visitPrev;
                    if (input == element) {
                        hasVisitedInput = true;
                        canVisit = includingSelf;
                    }
                    if (canVisit) {
                        outputConsumer.accept(element);
                    }
                }
            }
        });
    }
}

