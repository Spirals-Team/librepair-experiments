package spoon.test.trycatch;


public class Main {
    public void test() {
        try {
            java.lang.System.out.println("test");
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("test"))) {
            br.readLine();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}

