package spoon.test.prettyprinter.testclasses;


@java.lang.Deprecated
public abstract class ToBeChanged<T, K> extends java.util.ArrayList<T> implements java.lang.Cloneable , java.util.List<T> {
    private final java.lang.String string = "a" + (("b" + "c") + "d");

    public <T, K> void andSomeOtherMethod(int param1, java.lang.String param2, java.util.List<?>[][]... twoDArrayOfLists) {
        java.lang.System.out.println(("aaa" + "xyz"));
    }

    java.util.List<?>[][] twoDArrayOfLists = new java.util.List<?>[7][];
}

