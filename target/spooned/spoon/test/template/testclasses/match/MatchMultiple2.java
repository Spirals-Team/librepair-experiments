package spoon.test.template.testclasses.match;


public class MatchMultiple2 {
    public void matcher1(java.util.List<java.lang.String> something) {
        statements1.S();
        statements2.S();
        for (java.lang.String v : something) {
            java.lang.System.out.println(v);
        }
    }

    spoon.template.TemplateParameter<java.lang.Void> statements1;

    spoon.template.TemplateParameter<java.lang.Void> statements2;

    public void testMatch1() {
        int i = 0;
        i++;
        java.lang.System.out.println(i);
        java.lang.System.out.println("Xxxx");
        java.lang.System.out.println(((java.lang.String) (null)));
        java.lang.System.out.println("last one");
    }
}

