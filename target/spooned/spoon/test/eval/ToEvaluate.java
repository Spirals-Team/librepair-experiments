package spoon.test.eval;


public class ToEvaluate {
    static final java.lang.String S1 = "S1";

    static final java.lang.String S2 = "S2";

    static final java.lang.String S1S2_1 = (spoon.test.eval.ToEvaluate.S1) + "S2";

    static final java.lang.String S1S2_2 = (spoon.test.eval.ToEvaluate.S1) + (spoon.test.eval.ToEvaluate.S2);

    static final int I1 = 10;

    public static void testStrings() {
        if (!(spoon.test.eval.ToEvaluate.S1S2_1.equals(((spoon.test.eval.ToEvaluate.S1) + (spoon.test.eval.ToEvaluate.S2))))) {
            java.lang.System.out.println("dead code");
        }
        if (!(spoon.test.eval.ToEvaluate.S1S2_1.equals("S1S2"))) {
            java.lang.System.out.println("dead code");
        }
        if (!(spoon.test.eval.ToEvaluate.S1S2_2.equals("S1S2"))) {
            java.lang.System.out.println("dead code");
        }
        if (!(spoon.test.eval.ToEvaluate.S1S2_1.equals(spoon.test.eval.ToEvaluate.S1S2_2))) {
            java.lang.System.out.println("dead code");
        }
    }

    @java.lang.SuppressWarnings("unused")
    public static void testInts() {
        if (((spoon.test.eval.ToEvaluate.I1) + 1) != 11) {
            java.lang.System.out.println("dead code");
        }
    }

    public static void testArray() {
        if ((new java.lang.String[]{ "a", null, "b" }.length) == 11) {
            java.lang.System.out.println("dead code");
        }
    }

    public static void testDoNotSimplify(java.lang.String className, java.lang.String methodName) {
        java.lang.System.out.println(((("enter: " + className) + " - ") + methodName));
    }

    public static <U> U testDoNotSimplifyCasts(spoon.reflect.declaration.CtElement element) {
        return ((U) ((java.lang.Object) (spoon.test.eval.ToEvaluate.castTarget(element).getClass())));
    }

    private static <T> T castTarget(spoon.reflect.declaration.CtElement element) {
        return ((T) (element));
    }

    private static java.lang.String tryCatchAndStatement(spoon.reflect.declaration.CtElement element) {
        try {
            element.getClass();
        } catch (java.lang.RuntimeException e) {
            throw e;
        }
        return "This must not be removed";
    }

    private static java.lang.String simplifyOnlyWhenPossible(spoon.reflect.declaration.CtElement element) {
        spoon.test.eval.ToEvaluate.class.getName();
        java.lang.System.out.println(spoon.test.eval.ToEvaluate.class.getClassLoader());
        return spoon.test.eval.ToEvaluate.class.getName();
    }
}

