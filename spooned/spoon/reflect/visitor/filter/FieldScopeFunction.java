package spoon.reflect.visitor.filter;


public class FieldScopeFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtField<?>> {
    public FieldScopeFunction() {
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtField<?> field, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        if (field.hasModifier(spoon.reflect.declaration.ModifierKind.PRIVATE)) {
            searchForPrivateField(field, outputConsumer);
        }else
            if (field.hasModifier(spoon.reflect.declaration.ModifierKind.PUBLIC)) {
                searchForPublicField(field, outputConsumer);
            }else
                if (field.hasModifier(spoon.reflect.declaration.ModifierKind.PROTECTED)) {
                    searchForProtectedField(field, outputConsumer);
                }else {
                    searchForPackageProtectedField(field, outputConsumer);
                }


    }

    protected void searchForPrivateField(spoon.reflect.declaration.CtField<?> field, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        field.getTopLevelType().filterChildren(null).forEach(outputConsumer);
    }

    protected void searchForProtectedField(spoon.reflect.declaration.CtField<?> field, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        field.getFactory().getModel().filterChildren(new spoon.reflect.visitor.filter.SubtypeFilter(field.getDeclaringType().getReference())).filterChildren(null).forEach(outputConsumer);
    }

    protected void searchForPublicField(spoon.reflect.declaration.CtField<?> field, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        field.getFactory().getModel().filterChildren(null).forEach(outputConsumer);
    }

    protected void searchForPackageProtectedField(spoon.reflect.declaration.CtField<?> field, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        field.getTopLevelType().getPackage().filterChildren(null).forEach(outputConsumer);
    }
}

