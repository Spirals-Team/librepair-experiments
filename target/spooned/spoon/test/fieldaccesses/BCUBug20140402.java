package spoon.test.fieldaccesses;


public class BCUBug20140402 {
    java.lang.Object[] data = null;

    public void run() {
        int a = this.data.length;
    }
}

