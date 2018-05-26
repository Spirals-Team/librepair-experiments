package spoon.test.template.testclasses.logger;


public class Logger {
    public static void enter(java.lang.String className, java.lang.String methodName) {
        java.lang.System.out.println(((("enter: " + className) + " - ") + methodName));
    }

    public static void exit(java.lang.String methodName) {
        java.lang.System.err.println(("exit: " + methodName));
    }
}

