package spoon.generating.meta;


class RoleHandlerTemplate extends spoon.generating.meta.AbstractHandler<spoon.generating.meta.Node, spoon.generating.meta.ValueType> {
    private RoleHandlerTemplate() {
        super(spoon.generating.meta.$Role$.ROLE, spoon.generating.meta.$TargetType$.class, spoon.generating.meta.ValueType.class);
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <T, U> U getValue(T element) {
        return ((U) ((java.lang.Object) (castTarget(element).$getterName$())));
    }

    @java.lang.Override
    public <T, U> void setValue(T element, U value) {
        castTarget(element).$setterName$(castValue(value));
    }
}

