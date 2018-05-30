package spoon.reflect.code;


public interface CtStatement extends spoon.reflect.code.CtCodeElement {
    <T extends spoon.reflect.code.CtStatement> T insertAfter(spoon.reflect.code.CtStatement statement) throws spoon.reflect.declaration.ParentNotInitializedException;

    <T extends spoon.reflect.code.CtStatement> T insertAfter(spoon.reflect.code.CtStatementList statements) throws spoon.reflect.declaration.ParentNotInitializedException;

    <T extends spoon.reflect.code.CtStatement> T insertBefore(spoon.reflect.code.CtStatement statement) throws spoon.reflect.declaration.ParentNotInitializedException;

    <T extends spoon.reflect.code.CtStatement> T insertBefore(spoon.reflect.code.CtStatementList statements) throws spoon.reflect.declaration.ParentNotInitializedException;

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.LABEL)
    java.lang.String getLabel();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.LABEL)
    <T extends spoon.reflect.code.CtStatement> T setLabel(java.lang.String label);

    @java.lang.Override
    spoon.reflect.code.CtStatement clone();
}

