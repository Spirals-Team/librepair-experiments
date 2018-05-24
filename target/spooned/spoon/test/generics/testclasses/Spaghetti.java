package spoon.test.generics.testclasses;


@java.lang.SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class Spaghetti<B> {
    public interface This<K, V> {}

    public class That<K, V> implements spoon.test.generics.testclasses.Spaghetti.This<K, V> {}

    public class Tester implements spoon.test.generics.testclasses.Spaghetti.This<java.lang.String, B> {}

    private spoon.test.generics.testclasses.Spaghetti<B>.Tester tester;

    private spoon.test.generics.testclasses.Spaghetti<B>.Tester tester1;

    private spoon.test.generics.testclasses.Spaghetti<B>.That<java.lang.String, java.lang.String> field;

    private spoon.test.generics.testclasses.Spaghetti<java.lang.String>.That<java.lang.String, java.lang.String> field1;

    private spoon.test.generics.testclasses.Spaghetti<java.lang.Number>.That<java.lang.String, java.lang.String> field2;
}

