package spoon.support.reflect.code;


public class CtSynchronizedImpl extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtSynchronized {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> block;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<?> expression;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtSynchronized(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtBlock<?> getBlock() {
        return block;
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<?> getExpression() {
        return expression;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtSynchronized> T setBlock(spoon.reflect.code.CtBlock<?> block) {
        if (block != null) {
            block.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.BODY, block, this.block);
        this.block = block;
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtSynchronized> T setExpression(spoon.reflect.code.CtExpression<?> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, expression, this.expression);
        this.expression = expression;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtSynchronized clone() {
        return ((spoon.reflect.code.CtSynchronized) (super.clone()));
    }
}

