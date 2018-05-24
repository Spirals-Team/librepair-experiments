package spoon.support.reflect.code;


public class CtCatchImpl extends spoon.support.reflect.code.CtCodeElementImpl implements spoon.reflect.code.CtCatch {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> body;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.PARAMETER)
    spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable> parameter;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtCatch(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtBlock<?> getBody() {
        return body;
    }

    @java.lang.Override
    public spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable> getParameter() {
        return parameter;
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
    public <T extends spoon.reflect.code.CtCatch> T setParameter(spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable> parameter) {
        if (parameter != null) {
            parameter.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.PARAMETER, parameter, this.parameter);
        this.parameter = parameter;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtCatch clone() {
        return ((spoon.reflect.code.CtCatch) (super.clone()));
    }
}

