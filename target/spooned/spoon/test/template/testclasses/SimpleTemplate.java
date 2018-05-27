package spoon.test.template.testclasses;


public class SimpleTemplate implements spoon.template.Template<spoon.reflect.declaration.CtClass> {
    @spoon.template.Parameter
    java.lang.String _parameter_;

    @spoon.template.Local
    public SimpleTemplate(java.lang.String parameter) {
        _parameter_ = parameter;
    }

    public void simpleTemplateMethod() {
        java.lang.System.out.println(_parameter_);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtClass apply(spoon.reflect.declaration.CtType targetType) {
        spoon.template.Substitution.insertAll(targetType, this);
        return ((spoon.reflect.declaration.CtClass) (targetType));
    }
}

