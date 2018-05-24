package spoon.test.methodreference.testclasses;


public class Foo {
    private final java.util.List<spoon.test.methodreference.testclasses.Foo.Person> roster;

    private final spoon.test.methodreference.testclasses.Foo.Person[] rosterAsArray;

    private final spoon.test.methodreference.testclasses.Foo.Phone[] rosterPhoneAsArray = new spoon.test.methodreference.testclasses.Foo.Phone[10];

    public Foo() {
        roster = new java.util.ArrayList<>();
        rosterAsArray = roster.toArray(new spoon.test.methodreference.testclasses.Foo.Person[roster.size()]);
    }

    public void m() {
        java.util.Arrays.sort(rosterAsArray, spoon.test.methodreference.testclasses.Foo.Person::compareByAge);
    }

    public void m0() {
        final spoon.test.methodreference.testclasses.Foo.Person tarzan = new spoon.test.methodreference.testclasses.Foo.Person("Tarzan", 18, new spoon.test.methodreference.testclasses.Foo.Phone("0681273948"));
        java.util.Arrays.sort(rosterPhoneAsArray, tarzan.phone::compareByNumbers);
    }

    public void m1() {
        spoon.test.methodreference.testclasses.Foo.ComparisonProvider myComparisonProvider = new spoon.test.methodreference.testclasses.Foo.ComparisonProvider();
        java.util.Arrays.sort(rosterAsArray, myComparisonProvider::compareByName);
    }

    public void m2() {
        java.lang.String[] stringArray = new java.lang.String[]{ "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda" };
        java.util.Arrays.sort(stringArray, java.lang.String::compareToIgnoreCase);
    }

    public void m3() {
        spoon.test.methodreference.testclasses.Foo.transferElements(roster, java.util.HashSet<spoon.test.methodreference.testclasses.Foo.Person>::new);
    }

    public void m4() {
        spoon.test.methodreference.testclasses.Foo.personFactory(spoon.test.methodreference.testclasses.Foo.Person::new);
    }

    public void m5() {
        spoon.test.methodreference.testclasses.Foo.typeFactory(spoon.test.methodreference.testclasses.Foo.Type<java.lang.String>::new);
    }

    class ComparisonProvider {
        public int compareByName(spoon.test.methodreference.testclasses.Foo.Person a, spoon.test.methodreference.testclasses.Foo.Person b) {
            return a.name.compareTo(b.name);
        }

        public int compareByAge(spoon.test.methodreference.testclasses.Foo.Person a, spoon.test.methodreference.testclasses.Foo.Person b) {
            return (a.age) - (b.age);
        }
    }

    public static final class Person {
        public final java.lang.String name;

        public final int age;

        public final spoon.test.methodreference.testclasses.Foo.Phone phone;

        public Person() {
            name = "";
            age = 0;
            phone = null;
        }

        public Person(java.lang.String name, int age, spoon.test.methodreference.testclasses.Foo.Phone phone) {
            this.name = name;
            this.age = age;
            this.phone = phone;
        }

        public static int compareByAge(spoon.test.methodreference.testclasses.Foo.Person a, spoon.test.methodreference.testclasses.Foo.Person b) {
            return (a.age) - (b.age);
        }
    }

    public class Type<T> {}

    public static final class Phone {
        public final java.lang.String numbers;

        public Phone(java.lang.String numbers) {
            this.numbers = numbers;
        }

        public int compareByNumbers(spoon.test.methodreference.testclasses.Foo.Phone a, spoon.test.methodreference.testclasses.Foo.Phone b) {
            return a.numbers.compareTo(b.numbers);
        }
    }

    public static <T, SOURCE extends java.util.Collection<T>, DEST extends java.util.Collection<T>> DEST transferElements(SOURCE sourceCollection, java.util.function.Supplier<DEST> collectionFactory) {
        DEST result = collectionFactory.get();
        for (T t : sourceCollection) {
            result.add(t);
        }
        return result;
    }

    public static void personFactory(java.util.function.Supplier<spoon.test.methodreference.testclasses.Foo.Person> personFactory) {
    }

    public static void typeFactory(java.util.function.Supplier<spoon.test.methodreference.testclasses.Foo.Type> typeFactory) {
    }
}

