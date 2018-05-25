package spoon.generating.clone;


public class SetterTemplateMatcher {
    private boolean isElement;

    public <E extends spoon.reflect.declaration.CtElement> E setElement(boolean isElement) {
        this.isElement = isElement;
        return ((E) (this));
    }
}

