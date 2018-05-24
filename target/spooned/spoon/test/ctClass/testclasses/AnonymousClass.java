package spoon.test.ctClass.testclasses;


public class AnonymousClass {
    final int machin = new java.util.Comparator<java.lang.Integer>() {
        @java.lang.Override
        public int compare(java.lang.Integer o1, java.lang.Integer o2) {
            return 0;
        }
    }.compare(1, 2);
}

