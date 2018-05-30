package spoon.test.reference;


public enum Enum {
    A, B, C;
    public static spoon.test.reference.Enum getFirst() {
        return spoon.test.reference.Enum.valueOf("A");
    }
}

