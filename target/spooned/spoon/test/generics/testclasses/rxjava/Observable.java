package spoon.test.generics.testclasses.rxjava;


public class Observable<T> implements spoon.test.generics.testclasses.rxjava.Publisher<T> {
    public final void subscribe(spoon.test.generics.testclasses.rxjava.Subscriber<? super T> s) {
        java.util.Objects.requireNonNull(s);
    }
}

