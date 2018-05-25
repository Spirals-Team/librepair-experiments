package spoon.test.casts;


public class Castings {
    private java.lang.String s = ((java.lang.@spoon.test.casts.Castings.TypeAnnotation(integer = 1)
    String) (""));

    public void test(double a) {
    }

    public void foo() {
        java.util.List<java.lang.Integer> list = new java.util.ArrayList<java.lang.Integer>(1);
        list.add(1);
        test(getValue(list));
    }

    public void bar() {
        java.lang.String s = ((java.lang.@spoon.test.casts.Castings.TypeAnnotation(integer = 1)
        String) (""));
    }

    public final <T> T getValue(java.util.List<T> list) {
        return list.get(0);
    }

    @java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE_USE })
    @interface TypeAnnotation {
        int integer() default 0;
    }
}

