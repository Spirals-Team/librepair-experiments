package spoon.support.reflect.code;


public class CtForImpl extends spoon.support.reflect.code.CtLoopImpl implements spoon.reflect.code.CtFor {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> expression;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.FOR_INIT)
    java.util.List<spoon.reflect.code.CtStatement> forInit = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.FOR_UPDATE)
    java.util.List<spoon.reflect.code.CtStatement> forUpdate = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtFor(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<java.lang.Boolean> getExpression() {
        return expression;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtFor> T setExpression(spoon.reflect.code.CtExpression<java.lang.Boolean> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, expression, this.expression);
        this.expression = expression;
        return ((T) (this));
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtStatement> getForInit() {
        return forInit;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtFor> T addForInit(spoon.reflect.code.CtStatement statement) {
        if (statement == null) {
            return ((T) (this));
        }
        if ((forInit) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtStatement>emptyList())) {
            forInit = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.FOR_INIT_STATEMENTS_CONTAINER_DEFAULT_CAPACITY);
        }
        statement.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.FOR_INIT, this.forInit, statement);
        forInit.add(statement);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtFor> T setForInit(java.util.List<spoon.reflect.code.CtStatement> statements) {
        if ((statements == null) || (statements.isEmpty())) {
            this.forInit = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.FOR_INIT, this.forInit, new java.util.ArrayList<>(this.forInit));
        this.forInit.clear();
        for (spoon.reflect.code.CtStatement stmt : statements) {
            addForInit(stmt);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public boolean removeForInit(spoon.reflect.code.CtStatement statement) {
        if ((forInit) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtStatement>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.FOR_INIT, forInit, forInit.indexOf(statement), statement);
        return forInit.remove(statement);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtStatement> getForUpdate() {
        return forUpdate;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtFor> T addForUpdate(spoon.reflect.code.CtStatement statement) {
        if (statement == null) {
            return ((T) (this));
        }
        if ((forUpdate) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtStatement>emptyList())) {
            forUpdate = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.FOR_UPDATE_STATEMENTS_CONTAINER_DEFAULT_CAPACITY);
        }
        statement.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.FOR_UPDATE, this.forUpdate, statement);
        forUpdate.add(statement);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtFor> T setForUpdate(java.util.List<spoon.reflect.code.CtStatement> statements) {
        if ((statements == null) || (statements.isEmpty())) {
            this.forUpdate = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.FOR_UPDATE, this.forUpdate, new java.util.ArrayList<>(this.forUpdate));
        this.forUpdate.clear();
        for (spoon.reflect.code.CtStatement stmt : statements) {
            addForUpdate(stmt);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public boolean removeForUpdate(spoon.reflect.code.CtStatement statement) {
        if ((forUpdate) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtStatement>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.FOR_UPDATE, forUpdate, forUpdate.indexOf(statement), statement);
        return forUpdate.remove(statement);
    }

    @java.lang.Override
    public spoon.reflect.code.CtFor clone() {
        return ((spoon.reflect.code.CtFor) (super.clone()));
    }
}

