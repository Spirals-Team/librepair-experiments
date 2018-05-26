package spoon.support.reflect.code;


public class CtUnaryOperatorImpl<T> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtUnaryOperator<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    spoon.reflect.code.UnaryOperatorKind kind;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.LABEL)
    java.lang.String label;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<T> operand;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtUnaryOperator(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> getOperand() {
        return operand;
    }

    @java.lang.Override
    public spoon.reflect.code.UnaryOperatorKind getKind() {
        return kind;
    }

    @java.lang.Override
    public java.lang.String getLabel() {
        return label;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertAfter(spoon.reflect.code.CtStatement statement) {
        spoon.support.reflect.code.CtStatementImpl.insertAfter(this, statement);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertBefore(spoon.reflect.code.CtStatement statement) {
        spoon.support.reflect.code.CtStatementImpl.insertBefore(this, statement);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertAfter(spoon.reflect.code.CtStatementList statements) {
        spoon.support.reflect.code.CtStatementImpl.insertAfter(this, statements);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertBefore(spoon.reflect.code.CtStatementList statements) {
        spoon.support.reflect.code.CtStatementImpl.insertBefore(this, statements);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtUnaryOperator> C setOperand(spoon.reflect.code.CtExpression<T> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, expression, this.operand);
        this.operand = expression;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtUnaryOperator> C setKind(spoon.reflect.code.UnaryOperatorKind kind) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.OPERATOR_KIND, kind, this.kind);
        this.kind = kind;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C setLabel(java.lang.String label) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.LABEL, label, this.label);
        this.label = label;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtUnaryOperator<T> clone() {
        return ((spoon.reflect.code.CtUnaryOperator<T>) (super.clone()));
    }
}

