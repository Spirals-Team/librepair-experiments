package spoon.test.template.testclasses;


public class SecurityCheckerTemplate {
    public spoon.template.TemplateParameter<spoon.test.template.testclasses.ContextHelper> _ctx_;

    public spoon.reflect.code.CtLiteral<java.lang.String> _p_;

    public void matcher1() {
        if (!(_ctx_.S().hasPermission(_p_.S()))) {
            throw new java.lang.SecurityException();
        }
    }
}

