package spoon.test.generics.testclasses;


public class CelebrationLunch<K, L, M> extends spoon.test.generics.testclasses.Lunch<M, K> {
    public class WeddingLunch<X> extends spoon.test.generics.testclasses.CelebrationLunch<spoon.test.generics.testclasses.Tacos, spoon.test.generics.testclasses.Paella, X> {
        class Section<Y> {
            <S> void reserve(S section) {
            }
        }

        @java.lang.Override
        <C> void eatMe(X paramA, spoon.test.generics.testclasses.Tacos paramB, C paramC) {
        }
    }

    public class WeddingLunch2<X> {
        class Section<Y> {
            <S> void reserve(S section) {
            }
        }
    }

    @java.lang.Override
    <C> void eatMe(M paramA, K paramB, C paramC) {
    }

    <R> void prepare(R cook) {
    }

    void celebrate() {
        spoon.test.generics.testclasses.CelebrationLunch<java.lang.Integer, java.lang.Long, java.lang.Double> cl = new spoon.test.generics.testclasses.CelebrationLunch<>();
        spoon.test.generics.testclasses.CelebrationLunch<java.lang.Integer, java.lang.Long, java.lang.Double>.WeddingLunch<spoon.test.generics.testclasses.Mole> disgust = cl.new WeddingLunch<>();
        disgust.<spoon.test.generics.testclasses.Tacos>prepare(new spoon.test.generics.testclasses.Tacos());
        spoon.test.generics.testclasses.CelebrationLunch<java.lang.Integer, java.lang.Long, java.lang.Double>.WeddingLunch<spoon.test.generics.testclasses.Mole>.Section<spoon.test.generics.testclasses.Paella> section = disgust.new Section<>();
        section.<spoon.test.generics.testclasses.Tacos>reserve(null);
    }
}

