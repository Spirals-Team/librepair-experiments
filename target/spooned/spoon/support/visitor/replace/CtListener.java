package spoon.support.visitor.replace;


class CtListener implements spoon.support.visitor.replace.ReplaceListener<spoon.reflect.declaration.CtElement> {
    private final spoon.reflect.declaration.CtElement element;

    CtListener(spoon.reflect.declaration.CtElement element) {
        this.element = element;
    }

    @java.lang.Override
    public void set(spoon.reflect.declaration.CtElement replace) {
    }
}

