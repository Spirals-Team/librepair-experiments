package spoon.generating.clone;


class CloneVisitorTemplate extends spoon.reflect.visitor.CtScanner {
    private final spoon.support.visitor.equals.CloneHelper cloneHelper;

    private final spoon.support.visitor.clone.CloneBuilder builder = new spoon.support.visitor.clone.CloneBuilder();

    private spoon.reflect.declaration.CtElement other;

    CloneVisitorTemplate(spoon.support.visitor.equals.CloneHelper cloneHelper) {
        this.cloneHelper = cloneHelper;
    }

    public <T extends spoon.reflect.declaration.CtElement> T getClone() {
        return ((T) (other));
    }
}

