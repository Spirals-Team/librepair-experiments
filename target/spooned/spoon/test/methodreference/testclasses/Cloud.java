package spoon.test.methodreference.testclasses;


public class Cloud<T extends java.lang.String> {
    <U extends java.io.InputStream> void method(T param, U param2) {
    }

    <U extends java.io.Reader> void method(T param, U param2) {
    }

    <U extends java.util.List<? extends java.io.InputStream>> void method(T param, U param2) {
    }
}

