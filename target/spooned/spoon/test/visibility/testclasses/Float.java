package spoon.test.visibility.testclasses;


public class Float {
    public static float sum(float a, float b) {
        return a + b;
    }

    public static java.lang.Float aMethodNotInJavaLangFloatClass(java.lang.String param1, java.lang.String param2) {
        return 0.0F;
    }

    public void aMethodNotStatic() {
    }
}

