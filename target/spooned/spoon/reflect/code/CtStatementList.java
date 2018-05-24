package spoon.reflect.code;


public interface CtStatementList extends java.lang.Iterable<spoon.reflect.code.CtStatement> , spoon.reflect.code.CtCodeElement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.STATEMENT)
    java.util.List<spoon.reflect.code.CtStatement> getStatements();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T setStatements(java.util.List<spoon.reflect.code.CtStatement> statements);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T addStatement(spoon.reflect.code.CtStatement statement);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T addStatement(int index, spoon.reflect.code.CtStatement statement);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T insertBegin(spoon.reflect.code.CtStatement statement);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T insertBegin(spoon.reflect.code.CtStatementList statements);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T insertEnd(spoon.reflect.code.CtStatement statement);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T insertEnd(spoon.reflect.code.CtStatementList statements);

    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatementList> T insertBefore(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatement statement);

    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatementList> T insertBefore(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatementList statements);

    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatementList> T insertAfter(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatement statement);

    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatementList> T insertAfter(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatementList statements);

    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatement> T getStatement(int i);

    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatement> T getLastStatement();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    void removeStatement(spoon.reflect.code.CtStatement statement);

    @java.lang.Override
    spoon.reflect.code.CtStatementList clone();
}

