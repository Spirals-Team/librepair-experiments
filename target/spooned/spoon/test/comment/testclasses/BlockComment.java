package spoon.test.comment.testclasses;


public class BlockComment {
    private int field = 10;

    static {
    }

    public BlockComment() {
    }

    public void m() {
    }

    public void m1() {
        switch (1) {
            case 0 :
            case 1 :
                int i = 0;
            default :
        }
        for (int i = 0; i < 10; i++) {
        }
        if ((1 % 2) == 0) {
            (field)++;
        }
        new spoon.test.comment.testclasses.BlockComment();
        this.m();
        int i = 0;
        int j = 2;
        do {
            i++;
        } while (i < 10 );
        try {
            i++;
        } catch (java.lang.Exception e) {
        }
        synchronized(this) {
        }
        return;
    }

    public void m2(int i) throws java.lang.Error, java.lang.Exception {
    }

    public void m3() {
        if (true) {
        }else {
        }
        if (true)
            m3();
        else
            m3();

    }
}

