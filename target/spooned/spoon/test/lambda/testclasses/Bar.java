package spoon.test.lambda.testclasses;


public class Bar<T> {
    public static <T> spoon.test.lambda.testclasses.Bar<T> m(java.util.concurrent.CompletableFuture<? extends T> future) {
        java.util.Objects.requireNonNull(future);
        return spoon.test.lambda.testclasses.Bar.create(( s) -> {
            future.whenComplete(( v, e) -> {
            });
        });
    }

    public static <T> spoon.test.lambda.testclasses.Bar<T> create(spoon.test.lambda.testclasses.Bar.SingleOnSubscribe<T> onSubscribe) {
        return new spoon.test.lambda.testclasses.Bar<>();
    }

    public interface SingleOnSubscribe<T> extends java.util.function.Consumer<spoon.test.lambda.testclasses.Bar.SingleSubscriber<? super T>> {}

    public interface SingleSubscriber<T> {
        void onSubscribe(spoon.test.lambda.testclasses.Bar.Disposable d);

        void onSuccess(T value);

        void onError(java.lang.Throwable e);
    }

    public interface Disposable {
        void dispose();
    }
}

