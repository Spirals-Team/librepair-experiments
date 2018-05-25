package spoon.test.generics.testclasses;


class SubCelebrationLunch<K2, L2, M2> extends spoon.test.generics.testclasses.CelebrationLunch<K2, L2, M2> {
    public class SubWeddingLunch<X2> extends spoon.test.generics.testclasses.CelebrationLunch<K2, L2, M2>.WeddingLunch2<X2> {}
}

