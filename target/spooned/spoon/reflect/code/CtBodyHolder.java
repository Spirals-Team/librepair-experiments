package spoon.reflect.code;


public interface CtBodyHolder extends spoon.reflect.declaration.CtElement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtStatement getBody();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.BODY)
    <T extends spoon.reflect.code.CtBodyHolder> T setBody(spoon.reflect.code.CtStatement body);
}

