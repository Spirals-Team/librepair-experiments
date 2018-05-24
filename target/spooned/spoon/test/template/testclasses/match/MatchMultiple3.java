package spoon.test.template.testclasses.match;


public class MatchMultiple3 {
    public void matcher1() {
        statements1.S();
        statements2.S();
        java.lang.System.out.println("something");
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

