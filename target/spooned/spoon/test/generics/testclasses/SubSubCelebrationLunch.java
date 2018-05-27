package spoon.test.generics.testclasses;


class SubSubCelebrationLunch<K2, L2, M2> extends spoon.test.generics.testclasses.SubCelebrationLunch<K2, L2, M2> {
    public class SubSubWeddingLunch<X2> extends spoon.test.generics.testclasses.SubCelebrationLunch<K2, L2, M2>.SubWeddingLunch<X2> {
        class SubSection<Y2> extends spoon.test.generics.testclasses.CelebrationLunch<K2, L2, M2>.WeddingLunch2<X2>.Section<Y2> {}
    }
}

