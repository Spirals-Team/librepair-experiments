package spoon.test.template.testclasses.match;


public class MatchForEach2 {
    public void matcher1(java.util.List<java.lang.String> values) {
        int var = 0;
        for (java.lang.String value : values) {
            java.lang.System.out.println(value);
            var++;
        }
    }

    public void testMatch1() {
        java.lang.System.out.println("a");
        int cc = 0;
        java.lang.System.out.println("Xxxx");
        cc++;
        java.lang.System.out.println(((java.lang.String) (null)));
        cc++;
        int dd = 0;
        java.lang.System.out.println(java.lang.Long.class.toString());
        dd++;
    }
}

