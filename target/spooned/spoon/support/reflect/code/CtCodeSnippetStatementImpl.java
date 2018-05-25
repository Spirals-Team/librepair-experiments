package spoon.support.reflect.code;


public class CtCodeSnippetStatementImpl extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtCodeSnippetStatement {
    private static final long serialVersionUID = 1L;

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtCodeSnippetStatement(this);
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
    public <S extends spoon.reflect.code.CtStatement> S compile() throws spoon.support.compiler.SnippetCompilationError {
        return ((S) (spoon.support.compiler.SnippetCompilationHelper.compileStatement(this)));
    }

    @java.lang.Override
    public spoon.reflect.code.CtCodeSnippetStatement clone() {
        return ((spoon.reflect.code.CtCodeSnippetStatement) (super.clone()));
    }
}

