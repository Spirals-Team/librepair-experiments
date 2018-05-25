package spoon.test.fieldaccesses.testclasses;


public class Panini {
    int i;

    public spoon.test.fieldaccesses.testclasses.Panini.Sandwich m() {
        return new spoon.test.fieldaccesses.testclasses.Panini.Sandwich() {
            spoon.test.fieldaccesses.testclasses.Panini.Ingredient ingredient;

            @java.lang.Override
            int m() {
                return ingredient.next;
            }
        };
    }

    public void make() {
        (i)++;
        ++(i);
    }

    public void prepare() {
        i += 0;
        int j = 0;
        j += 0;
        int[] array = new int[]{  };
        array[0] += 0;
    }

    abstract class Sandwich {
        abstract int m();
    }

    class Ingredient {
        int next;
    }
}

