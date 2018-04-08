package click.dobel.alexasonic.docker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import click.dobel.alexasonic.test.AlexaSonicIntegrationTest;

public class AlexaSonicDockerTest extends AlexaSonicIntegrationTest {

    @Test
    public void test() {
        System.out.println("=====================");
        System.out.println("Docker ports exposed:");
        docker.containers().container(CONTAINER_AIRSONIC).ports().stream().forEach(p -> {
            System.out.println(String.format("H: %s I: %s E: %s", p.getIp(), p.getInternalPort(), p.getExternalPort()));
        });
        System.out.println("=====================");
    }

    @After
    public void showLogs() throws IOException {
        System.out.println("=====================");
        System.out.println("Log file of airsonic container:");
        final List<String> lines = Files.readAllLines(Paths.get(LOGFILE_PATH + "/airsonic.log"));
        lines.forEach(System.out::println);
        System.out.println("=====================");
    }

}
