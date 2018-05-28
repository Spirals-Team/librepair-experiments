package spoon.generating.clone;


class CloneBuilderTemplate extends spoon.reflect.visitor.CtInheritanceScanner {
    public void copy(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        this.setOther(other);
        this.scan(element);
    }

    public static <T extends spoon.reflect.declaration.CtElement> T build(spoon.generating.clone.CloneBuilderTemplate builder, spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        builder.setOther(other);
        builder.scan(element);
        return ((T) (builder.other));
    }

    private spoon.reflect.declaration.CtElement other;

    public void setOther(spoon.reflect.declaration.CtElement other) {
        this.other = other;
    }
}

