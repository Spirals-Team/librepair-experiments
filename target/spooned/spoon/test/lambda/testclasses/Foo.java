package spoon.test.lambda.testclasses;


public class Foo {
    private java.util.List<spoon.test.lambda.testclasses.Foo.Person> persons = new java.util.ArrayList<>();

    public void m() {
        spoon.test.lambda.testclasses.Foo.printPersonsWithCheck(persons, ((spoon.test.lambda.testclasses.Foo.Check) (() -> false)));
    }

    public void m2() {
        spoon.test.lambda.testclasses.Foo.printPersonsWithPredicate(persons, ((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>) (( p) -> (p.age) > 10)));
    }

    public void m3() {
        spoon.test.lambda.testclasses.Foo.printPersonsWithCheckPersons(persons, ((spoon.test.lambda.testclasses.Foo.CheckPersons) (( p1, p2) -> ((p1.age) - (p2.age)) > 0)));
    }

    public void m4() {
        spoon.test.lambda.testclasses.Foo.printPersonsWithPredicate(persons, ((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>) ((spoon.test.lambda.testclasses.Foo.Person p) -> (p.age) > 10)));
    }

    public void m5() {
        spoon.test.lambda.testclasses.Foo.printPersonsWithCheckPersons(persons, ((spoon.test.lambda.testclasses.Foo.CheckPersons) ((spoon.test.lambda.testclasses.Foo.Person p1,spoon.test.lambda.testclasses.Foo.Person p2) -> ((p1.age) - (p2.age)) > 0)));
    }

    public void m6() {
        spoon.test.lambda.testclasses.Foo.printPersonsWithCheck(persons, ((spoon.test.lambda.testclasses.Foo.Check) (() -> {
            java.lang.System.err.println("");
            return false;
        })));
    }

    public void m7() {
        spoon.test.lambda.testclasses.Foo.printPersonsWithPredicate(persons, ((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>) (( p) -> {
            p.doSomething();
            return (p.age) > 10;
        })));
    }

    public void m8() {
        if (((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>) (( p) -> (p.age) > 18)).test(new spoon.test.lambda.testclasses.Foo.Person(10))) {
            java.lang.System.err.println("Enjoy, you have more than 18.");
        }
    }

    public void m9() {
        java.util.function.Consumer<java.lang.Integer> c = ( field) -> {
            field = 1;
        };
    }

    public static void printPersonsWithPredicate(java.util.List<spoon.test.lambda.testclasses.Foo.Person> roster, java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person> tester) {
        for (spoon.test.lambda.testclasses.Foo.Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    public static void printPersonsWithCheckPerson(java.util.List<spoon.test.lambda.testclasses.Foo.Person> roster, spoon.test.lambda.testclasses.Foo.CheckPerson tester) throws java.lang.Exception {
        for (spoon.test.lambda.testclasses.Foo.Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    public static void printPersonsWithCheck(java.util.List<spoon.test.lambda.testclasses.Foo.Person> roster, spoon.test.lambda.testclasses.Foo.Check tester) {
        for (spoon.test.lambda.testclasses.Foo.Person p : roster) {
            if (tester.test()) {
                p.printPerson();
            }
        }
    }

    public static void printPersonsWithCheckPersons(java.util.List<spoon.test.lambda.testclasses.Foo.Person> roster, spoon.test.lambda.testclasses.Foo.CheckPersons tester) {
        if (tester.test(roster.get(0), roster.get(1))) {
            roster.get(0).printPerson();
        }
    }

    public class Person {
        public final int age;

        public Person(int age) {
            this.age = age;
        }

        public void printPerson() {
            java.lang.System.out.println(this.toString());
        }

        public void doSomething() {
        }
    }

    public interface CheckPerson {
        boolean test(spoon.test.lambda.testclasses.Foo.Person p) throws java.lang.Exception;
    }

    public interface Check {
        boolean test();
    }

    public interface CheckPersons {
        boolean test(spoon.test.lambda.testclasses.Foo.Person p1, spoon.test.lambda.testclasses.Foo.Person p2);
    }
}

