package spoon.test.generics.testclasses;


public class Tacos<K, V extends java.lang.String> implements spoon.test.generics.testclasses.ITacos<V> {
    public Tacos() {
        <java.lang.String>this(1);
    }

    public <T> Tacos(int nbTacos) {
    }

    public void m() {
        java.util.List<java.lang.String> l = new java.util.ArrayList<>();
        java.util.List l2;
        spoon.test.generics.testclasses.IBurritos<?, ?> burritos = new Burritos<>();
        java.util.List<?> l3 = new java.util.ArrayList<java.lang.Object>();
        new <java.lang.Integer>spoon.test.generics.testclasses.Tacos<java.lang.Object, java.lang.String>();
        new spoon.test.generics.testclasses.Tacos<>();
    }

    public void m2() {
        this.<java.lang.String>makeTacos(null);
        this.makeTacos(null);
    }

    public void m3() {
        new javax.lang.model.util.SimpleTypeVisitor7<spoon.test.generics.testclasses.Tacos, java.lang.Void>() {};
        new javax.lang.model.util.SimpleTypeVisitor7<spoon.test.generics.testclasses.Tacos, java.lang.Void>() {};
    }

    public <V, C extends java.util.List<V>> void m4() {
        spoon.test.generics.testclasses.Tacos.<V, C>makeTacos();
        spoon.test.generics.testclasses.Tacos.makeTacos();
    }

    public static <V, C extends java.util.List<V>> java.util.List<C> makeTacos() {
        return null;
    }

    public <T> void makeTacos(T anObject) {
    }

    class Burritos<K, V> implements spoon.test.generics.testclasses.IBurritos<K, V> {
        spoon.test.generics.testclasses.Tacos<K, java.lang.String>.Burritos<K, V> burritos;

        public spoon.test.generics.testclasses.Tacos<K, java.lang.String>.Burritos<K, V> b() {
            new Burritos<K, V>();
            return null;
        }

        class Pozole {
            public spoon.test.generics.testclasses.Tacos<K, java.lang.String>.Burritos<K, V>.Pozole p() {
                new Pozole();
                return null;
            }
        }

        @java.lang.Override
        public spoon.test.generics.testclasses.IBurritos<K, V> make() {
            return new Burritos<K, V>() {};
        }
    }

    public class BeerFactory {
        public spoon.test.generics.testclasses.Tacos<K, V>.Beer newBeer() {
            return new Beer();
        }
    }

    class Beer {}
}

