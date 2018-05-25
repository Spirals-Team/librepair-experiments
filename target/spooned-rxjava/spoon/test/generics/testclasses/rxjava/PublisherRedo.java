package spoon.test.generics.testclasses.rxjava;


public final class PublisherRedo<T> implements spoon.test.generics.testclasses.rxjava.Publisher<T> {
    final java.util.function.Function<? super spoon.test.generics.testclasses.rxjava.Observable<spoon.test.generics.testclasses.rxjava.Try<java.util.Optional<java.lang.Object>>>, ? extends spoon.test.generics.testclasses.rxjava.Publisher<?>> manager;

    public PublisherRedo(java.util.function.Function<? super spoon.test.generics.testclasses.rxjava.Observable<spoon.test.generics.testclasses.rxjava.Try<java.util.Optional<java.lang.Object>>>, ? extends spoon.test.generics.testclasses.rxjava.Publisher<?>> manager) {
        this.manager = manager;
    }

    public void subscribe(spoon.test.generics.testclasses.rxjava.Subscriber<? super T> s) {
        spoon.test.generics.testclasses.rxjava.BehaviorSubject<spoon.test.generics.testclasses.rxjava.Try<java.util.Optional<java.lang.Object>>> subject = spoon.test.generics.testclasses.rxjava.BehaviorSubject.create();
        spoon.test.generics.testclasses.rxjava.PublisherRedo.RedoSubscriber<T> parent = new spoon.test.generics.testclasses.rxjava.PublisherRedo.RedoSubscriber<>();
        spoon.test.generics.testclasses.rxjava.Publisher<?> action = manager.apply(subject);
        action.subscribe(new spoon.test.generics.testclasses.rxjava.ToNotificationSubscriber<>(parent::handle));
    }

    private void subscribe(spoon.test.generics.testclasses.rxjava.ToNotificationSubscriber truc) {
    }

    static final class RedoSubscriber<T> extends java.util.concurrent.atomic.AtomicBoolean implements spoon.test.generics.testclasses.rxjava.Subscriber<T> {
        void handle(spoon.test.generics.testclasses.rxjava.Try<java.util.Optional<java.lang.Object>> notification) {
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
}

