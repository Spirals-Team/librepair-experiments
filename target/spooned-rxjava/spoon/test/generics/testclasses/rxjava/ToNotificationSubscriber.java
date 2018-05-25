package spoon.test.generics.testclasses.rxjava;


public final class ToNotificationSubscriber<T> implements spoon.test.generics.testclasses.rxjava.Subscriber<T> {
    final java.util.function.Consumer<? super spoon.test.generics.testclasses.rxjava.Try<java.util.Optional<java.lang.Object>>> consumer;

    public ToNotificationSubscriber(java.util.function.Consumer<? super spoon.test.generics.testclasses.rxjava.Try<java.util.Optional<java.lang.Object>>> consumer) {
        this.consumer = consumer;
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

