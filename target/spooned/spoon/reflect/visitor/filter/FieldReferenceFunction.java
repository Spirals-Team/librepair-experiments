package spoon.reflect.visitor.filter;


public class FieldReferenceFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> {
    private final spoon.reflect.declaration.CtField<?> field;

    public FieldReferenceFunction() {
        this.field = null;
    }

    public FieldReferenceFunction(spoon.reflect.declaration.CtField<?> field) {
        this.field = field;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtElement fieldOrScope, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        spoon.reflect.declaration.CtElement scope;
        spoon.reflect.declaration.CtField<?> field = this.field;
        if (field == null) {
            if (fieldOrScope instanceof spoon.reflect.declaration.CtField) {
                field = ((spoon.reflect.declaration.CtField<?>) (fieldOrScope));
            }else {
                throw new spoon.SpoonException(("The input of FieldReferenceFunction must be a CtField but is " + (fieldOrScope.getClass().getSimpleName())));
            }
            scope = field.getFactory().getModel().getUnnamedModule();
        }else {
            scope = fieldOrScope;
        }
        scope.filterChildren(new spoon.reflect.visitor.filter.DirectReferenceFilter<spoon.reflect.reference.CtFieldReference<?>>(field.getReference())).forEach(outputConsumer);
    }
}

