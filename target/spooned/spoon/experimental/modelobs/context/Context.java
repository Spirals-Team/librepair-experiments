package spoon.experimental.modelobs.context;


public abstract class Context {
    private spoon.reflect.declaration.CtElement elementWhereChangeHappens;

    private spoon.reflect.path.CtRole changedProperty;

    public Context(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole changedProperty) {
        this.elementWhereChangeHappens = element;
        this.changedProperty = changedProperty;
    }

    public spoon.reflect.declaration.CtElement getElementWhereChangeHappens() {
        return elementWhereChangeHappens;
    }

    public spoon.reflect.path.CtRole getChangedProperty() {
        return changedProperty;
    }
}

