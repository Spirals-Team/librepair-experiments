package spoon.test.fieldaccesses;


public class Mouse {
    int[] is;

    int age;

    spoon.test.fieldaccesses.Mouse son;

    @java.lang.SuppressWarnings("unused")
    public void meth1() {
        age = 3;
        son = new spoon.test.fieldaccesses.Mouse();
        int l = age;
    }

    public void meth1b() {
        this.age = 3;
        son = new spoon.test.fieldaccesses.Mouse();
    }

    public void meth2() {
        this.son.age = 3;
    }

    public void meth3() {
        age = is.length;
    }

    public void meth4() {
        is[2] = 4;
    }
}

