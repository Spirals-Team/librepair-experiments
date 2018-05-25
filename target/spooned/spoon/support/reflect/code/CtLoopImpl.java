package spoon.support.reflect.code;


public abstract class CtLoopImpl extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtLoop {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtStatement body;

    @java.lang.Override
    public spoon.reflect.code.CtStatement getBody() {
        return body;
    }

    @java.lang.SuppressWarnings("unchecked")
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
    public spoon.reflect.code.CtLoop clone() {
        return ((spoon.reflect.code.CtLoop) (super.clone()));
    }

    @java.lang.Override
    public java.lang.Void S() {
        return null;
    }

    public spoon.reflect.code.CtCodeElement getSubstitution(spoon.reflect.declaration.CtType<?> targetType) {
        return clone();
    }
}

