package spoon.test.targeted.testclasses;


public class Tapas<T> {
    public interface SingleOnSubscribe<T> extends java.util.function.Consumer<spoon.test.targeted.testclasses.Tapas.SingleSubscriber<? super T>> {}

    public interface SingleSubscriber<T> {}

    public static <T> spoon.test.targeted.testclasses.Tapas<T> create(spoon.test.targeted.testclasses.Tapas.SingleOnSubscribe<T> onSubscribe) {
        return new spoon.test.targeted.testclasses.Tapas<T>() {
            class InnerSubscriber implements spoon.test.targeted.testclasses.Tapas.SingleSubscriber<T> {
                int index;

                public InnerSubscriber(int index) {
                    this.index = index;
                }
            }
        };
    }

    public static <T> spoon.test.targeted.testclasses.Tapas<T> create2() {
        return spoon.test.targeted.testclasses.Tapas.create(( s) -> {
            class InnerSubscriber implements spoon.test.targeted.testclasses.Tapas.SingleSubscriber<T> {
                long index;

                public InnerSubscriber(int index) {
                    this.index = index;
                }
            }
        });
    }

    public static <T> spoon.test.targeted.testclasses.Tapas<T> equals() {
        return spoon.test.targeted.testclasses.Tapas.create(( s) -> {
            class InnerSubscriber implements spoon.test.targeted.testclasses.Tapas.SingleSubscriber<T> {
                final int index;

                public InnerSubscriber(int index) {
                    this.index = index;
                }
            }
        });
    }
}

