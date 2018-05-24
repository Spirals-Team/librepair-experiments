package spoon.reflect.declaration;


public interface CtFormalTypeDeclarer extends spoon.reflect.declaration.CtTypeMember {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE_PARAMETER)
    java.util.List<spoon.reflect.declaration.CtTypeParameter> getFormalCtTypeParameters();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_PARAMETER)
    <T extends spoon.reflect.declaration.CtFormalTypeDeclarer> T setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_PARAMETER)
    <T extends spoon.reflect.declaration.CtFormalTypeDeclarer> T addFormalCtTypeParameter(spoon.reflect.declaration.CtTypeParameter formalTypeParameter);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_PARAMETER)
    boolean removeFormalCtTypeParameter(spoon.reflect.declaration.CtTypeParameter formalTypeParameter);
}

