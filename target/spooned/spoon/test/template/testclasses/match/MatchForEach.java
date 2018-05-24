package spoon.test.template.testclasses.match;


public class MatchForEach {
    public void matcher1(java.util.List<java.lang.String> values) {
        for (java.lang.String value : values) {
            java.lang.System.out.println(value);
        }
    }

    public void testMatch1() {
        java.lang.System.out.println("a");
        java.lang.System.out.println("Xxxx");
        java.lang.System.out.println(((java.lang.String) (null)));
        java.lang.System.out.println(java.lang.Long.class.toString());
    }
}

