package spoon.test.ctBlock.testclasses;


public class Toto {
    public void foo() {
        int i = 1;
        i++;
        if (i > 0) {
            java.lang.System.out.println("test");
        }
        i++;
    }

    public void bar() {
        switch ("truc") {
            case "t" :
                int i = 0;
                i++;
                java.lang.System.out.println(i);
            case "u" :
                i = 3;
                i = 4;
        }
    }
}

