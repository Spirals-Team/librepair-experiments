import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintWriter;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HelloWorldTest {

    @Mock
    PrintWriter writer;

    @Test
    public void helloWorldTest() {
        HelloWorld.helloWorld(writer);

        verify(writer).println("Hello World!");
        verify(writer).flush();

    }
}
