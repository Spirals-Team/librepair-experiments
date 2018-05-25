package spoon.test.enums;


public enum Regular {
    A, B, C;
    spoon.test.enums.Regular D;

    int i;

    public static void main(java.lang.String[] args) {
        for (spoon.test.enums.Regular e : spoon.test.enums.Regular.values()) {
            java.lang.System.out.println(e);
        }
    }
}

