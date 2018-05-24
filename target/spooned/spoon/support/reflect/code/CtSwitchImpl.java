package spoon.support.reflect.code;


public class CtSwitchImpl<S> extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtSwitch<S> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.CASE)
    java.util.List<spoon.reflect.code.CtCase<? super S>> cases = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<S> expression;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtSwitch(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtCase<? super S>> getCases() {
        return cases;
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<S> getSelector() {
        return expression;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtSwitch<S>> T setCases(java.util.List<spoon.reflect.code.CtCase<? super S>> cases) {
        if ((cases == null) || (cases.isEmpty())) {
            this.cases = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.CASE, this.cases, new java.util.ArrayList<>(this.cases));
        this.cases.clear();
        for (spoon.reflect.code.CtCase<? super S> aCase : cases) {
            addCase(aCase);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtSwitch<S>> T setSelector(spoon.reflect.code.CtExpression<S> selector) {
        if (selector != null) {
            selector.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, selector, this.expression);
        this.expression = selector;
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtSwitch<S>> T addCase(spoon.reflect.code.CtCase<? super S> c) {
        if (c == null) {
            return ((T) (this));
        }
        if ((cases) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtCase<? super S>>emptyList())) {
            cases = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.SWITCH_CASES_CONTAINER_DEFAULT_CAPACITY);
        }
        c.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.CASE, this.cases, c);
        cases.add(c);
        return ((T) (this));
    }

    @java.lang.Override
    public boolean removeCase(spoon.reflect.code.CtCase<? super S> c) {
        if ((cases) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtCase<? super S>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.CASE, cases, cases.indexOf(c), c);
        return cases.remove(c);
    }

    @java.lang.Override
    public spoon.reflect.code.CtSwitch<S> clone() {
        return ((spoon.reflect.code.CtSwitch<S>) (super.clone()));
    }
}

