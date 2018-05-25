package spoon.test.template.testclasses;


public class ToBeMatched {
    public void match1() {
        java.util.Arrays.asList("a", "b", "c", "d", "e", "f", "a", "b", "c", "d", "e");
    }

    public void match2() {
        java.util.Arrays.asList("a", "b", "b", "b", "c", "c", "d", "d", "d", "d", "d");
    }
}

