package spoon.test.limits;


public class StaticFieldAccesOnInstance {
    public static java.lang.String test = "";

    @java.lang.SuppressWarnings("static-access")
    void method() {
        spoon.test.limits.StaticFieldAccesOnInstance test2 = new spoon.test.limits.StaticFieldAccesOnInstance();
        java.lang.System.out.println(spoon.test.limits.StaticFieldAccesOnInstance.test);
        java.lang.System.out.println(test2.test);
    }
}

