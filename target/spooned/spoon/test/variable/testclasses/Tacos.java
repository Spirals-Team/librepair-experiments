package spoon.test.variable.testclasses;


public class Tacos {
    spoon.test.variable.testclasses.Tacos.Burritos burritos;

    final class Burritos {
        int i;
    }

    public void makeIt() {
        burritos.i = 4;
        final spoon.test.variable.testclasses.Tacos tacos = new spoon.test.variable.testclasses.Tacos();
        tacos.burritos.i = 3;
    }
}

