package spoon.support.reflect.code;


public class CtCodeSnippetExpressionImpl<T> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtCodeSnippetExpression<T> {
    private static final long serialVersionUID = 1L;

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtCodeSnippetExpression(this);
    }

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.SNIPPET)
    java.lang.String value;

    @java.lang.Override
    public java.lang.String getValue() {
        return value;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtCodeSnippet> C setValue(java.lang.String value) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.SNIPPET, value, this.value);
        this.value = value;
        return ((C) (this));
    }

    @java.lang.SuppressWarnings("unchecked")
    public <E extends spoon.reflect.code.CtExpression<T>> E compile() throws spoon.support.compiler.SnippetCompilationError {
        return ((E) (spoon.support.compiler.SnippetCompilationHelper.compileExpression(this)));
    }

    @java.lang.Override
    public spoon.reflect.code.CtCodeSnippetExpression<T> clone() {
        return ((spoon.reflect.code.CtCodeSnippetExpression<T>) (super.clone()));
    }
}

