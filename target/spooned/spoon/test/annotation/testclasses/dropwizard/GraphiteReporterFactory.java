package spoon.test.annotation.testclasses.dropwizard;


public class GraphiteReporterFactory {
    @spoon.test.annotation.testclasses.PortRange
    private int port = 2003;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

