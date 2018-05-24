package spoon.test.fieldaccesses.testclasses;


public class Pozole {
    interface Interface1 {}

    class Cook {
        public spoon.test.fieldaccesses.testclasses.Pozole.Interface1 m() {
            return null;
        }
    }

    public spoon.test.fieldaccesses.testclasses.Pozole.Cook cook() {
        return new spoon.test.fieldaccesses.testclasses.Pozole.Cook() {
            @java.lang.Override
            public spoon.test.fieldaccesses.testclasses.Pozole.Interface1 m() {
                return new spoon.test.fieldaccesses.testclasses.Pozole.Interface1() {
                    private final Test test = new Test();
                };
            }

            class Test implements spoon.test.fieldaccesses.testclasses.Pozole.Interface1 {}
        };
    }
}

