package spoon.test.template.testclasses.inheritance;


public class SubTemplate extends spoon.test.template.testclasses.inheritance.SuperTemplate {
    public void toBeOverriden() {
        super.toBeOverriden();
    }

    public void methodWithTemplatedParameters(java.lang.Object params) {
        java.util.List var = null;
        java.util.ArrayList l = null;
        java.util.List o = ((java.util.ArrayList) (new java.util.ArrayList()));
        invocation.S();
        {
            for (java.lang.Object x : intValues) {
                java.lang.System.out.println(x);
            }
        }
        for (java.lang.Object x : intValues) {
            java.lang.System.out.println(x);
        }
        for (java.lang.Object x : intValues) {
            {
                java.lang.System.out.println(x);
            }
        }
        for (java.lang.Object x : intValues)
            java.lang.System.out.println(x);

        for (java.lang.Object x : o) {
            java.lang.System.out.println(x);
        }
        var = o;
        l = ((java.util.ArrayList) (var));
    }

    java.util.List var = null;

    public void methodWithFieldAccess() {
        java.util.List o = ((java.util.ArrayList) (new java.util.ArrayList()));
        java.util.ArrayList l = null;
        var = o;
        l = ((java.util.ArrayList) (var));
    }

    void var() {
    }

    @spoon.template.Parameter
    public java.util.List<spoon.reflect.declaration.CtParameter> params;

    @spoon.template.Parameter("var")
    public java.lang.String param_var = "newVarName";

    @spoon.template.Parameter
    java.lang.Class ArrayList = java.util.LinkedList.class;

    @spoon.template.Parameter
    public spoon.reflect.code.CtInvocation invocation;

    @spoon.template.Parameter
    public spoon.reflect.code.CtExpression[] intValues;

    @spoon.template.Local
    public void ignoredMethod() {
    }

    class InnerClass {}
}

