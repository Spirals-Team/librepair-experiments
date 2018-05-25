package spoon.support.reflect.code;


public class CtTryImpl extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtTry {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> body;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.CATCH)
    java.util.List<spoon.reflect.code.CtCatch> catchers = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.FINALIZER)
    spoon.reflect.code.CtBlock<?> finalizer;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtTry(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtCatch> getCatchers() {
        return catchers;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtTry> T setCatchers(java.util.List<spoon.reflect.code.CtCatch> catchers) {
        if ((catchers == null) || (catchers.isEmpty())) {
            this.catchers = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.CATCH, this.catchers, new java.util.ArrayList<>(this.catchers));
        this.catchers.clear();
        for (spoon.reflect.code.CtCatch c : catchers) {
            addCatcher(c);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtTry> T addCatcher(spoon.reflect.code.CtCatch catcher) {
        if (catcher == null) {
            return ((T) (this));
        }
        if ((catchers) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtCatch>emptyList())) {
            catchers = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.CATCH_CASES_CONTAINER_DEFAULT_CAPACITY);
        }
        catcher.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.CATCH, this.catchers, catcher);
        catchers.add(catcher);
        return ((T) (this));
    }

    @java.lang.Override
    public boolean removeCatcher(spoon.reflect.code.CtCatch catcher) {
        if ((catchers) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtCatch>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.CATCH, catchers, catchers.indexOf(catcher), catcher);
        return catchers.remove(catcher);
    }

    @java.lang.Override
    public spoon.reflect.code.CtBlock<?> getFinalizer() {
        return finalizer;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtTry> T setFinalizer(spoon.reflect.code.CtBlock<?> finalizer) {
        if (finalizer != null) {
            finalizer.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.FINALIZER, finalizer, this.finalizer);
        this.finalizer = finalizer;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtBlock<?> getBody() {
        return body;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtBodyHolder> T setBody(spoon.reflect.code.CtStatement statement) {
        if (statement != null) {
            spoon.reflect.code.CtBlock<?> body = getFactory().Code().getOrCreateCtBlock(statement);
            getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.BODY, body, this.body);
            if (body != null) {
                body.setParent(this);
            }
            this.body = body;
        }else {
            getFactory().getEnvironment().getModelChangeListener().onObjectDelete(this, spoon.reflect.path.CtRole.BODY, this.body);
            this.body = null;
        }
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtTry clone() {
        return ((spoon.reflect.code.CtTry) (super.clone()));
    }

    @java.lang.Override
    public java.lang.Void S() {
        return null;
    }

    public spoon.reflect.code.CtCodeElement getSubstitution(spoon.reflect.declaration.CtType<?> targetType) {
        return clone();
    }
}

