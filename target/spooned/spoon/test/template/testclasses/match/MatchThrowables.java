package spoon.test.template.testclasses.match;


public class MatchThrowables {
    public void matcher1() throws java.io.FileNotFoundException, java.io.IOException {
    }

    public static void sample1() {
    }

    void sample2(int a, spoon.test.template.testclasses.match.MatchThrowables me) throws java.io.FileNotFoundException, java.io.IOException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
    }

    void sample3(int a, spoon.test.template.testclasses.match.MatchThrowables me) throws java.io.FileNotFoundException, java.io.IOException, java.lang.IllegalArgumentException {
    }

    private void sample4() throws java.io.FileNotFoundException, java.io.IOException {
        this.getClass();
        java.lang.System.out.println();
    }

    int noMatchBecauseReturnsInt() throws java.io.IOException, java.lang.Exception {
        return 0;
    }
}

