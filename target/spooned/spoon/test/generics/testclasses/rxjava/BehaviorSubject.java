package spoon.test.generics.testclasses.rxjava;


public final class BehaviorSubject<T> extends spoon.test.generics.testclasses.rxjava.Subject<T, T> {
    public static <T> spoon.test.generics.testclasses.rxjava.BehaviorSubject<T> create() {
        return new spoon.test.generics.testclasses.rxjava.BehaviorSubject<>();
    }

    @java.lang.Override
    public void onNext(T var1) {
    }

    @java.lang.Override
    public void onError(java.lang.Throwable var1) {
    }

    @java.lang.Override
    public void onComplete() {
    }
}

