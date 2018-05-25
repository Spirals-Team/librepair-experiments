package spoon.test.template.testclasses;


public class SubstituteLiteralTemplate extends spoon.template.ExtensionTemplate {
    java.lang.String stringField1 = "$param1$";

    java.lang.String stringField2 = "Substring $param1$ is substituted too - $param1$";

    void m1() {
        java.lang.System.out.println(spoon.test.template.testclasses.Params.$param1$());
    }

    @spoon.template.Parameter
    java.lang.Object $param1$;

    @spoon.template.Local
    public SubstituteLiteralTemplate(java.lang.Object param) {
        this.$param1$ = param;
    }
}

