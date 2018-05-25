package spoon.reflect.declaration;


public interface CtCodeSnippet {
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SNIPPET)
    <C extends spoon.reflect.declaration.CtCodeSnippet> C setValue(java.lang.String value);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SNIPPET)
    java.lang.String getValue();
}

