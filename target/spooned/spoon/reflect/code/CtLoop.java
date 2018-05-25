package spoon.reflect.code;


public interface CtLoop extends spoon.reflect.code.CtBodyHolder , spoon.reflect.code.CtStatement , spoon.template.TemplateParameter<java.lang.Void> {
    @java.lang.Override
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtStatement getBody();

    @java.lang.Override
    spoon.reflect.code.CtLoop clone();
}

