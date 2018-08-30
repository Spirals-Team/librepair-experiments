import org.junit.runner.RunWith;

import java.io.PrintWriter;

public class HelloWorld {
    public static void main (String [] args) {
        PrintWriter writer = new PrintWriter(System.out);
        helloWorld(writer);
        writer.close();
    }
    public static void helloWorld(PrintWriter writer) {
        writer.println("Hello !");
        writer.flush();
    }
}
