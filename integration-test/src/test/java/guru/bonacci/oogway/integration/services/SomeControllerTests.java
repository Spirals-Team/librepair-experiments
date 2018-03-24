package guru.bonacci.oogway.integration.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.configuration.ProjectName;
import com.palantir.docker.compose.connection.DockerPort;
import com.palantir.docker.compose.connection.waiting.HealthChecks;

import guru.bonacci.oogway.integration.SomeTestApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SomeTestApp.class, properties = {
	"spring.cloud.config.enabled=false",
	"eureka.client.enabled=false"
})
public class SomeControllerTests {

	private TestRestTemplate template = new TestRestTemplate();
	
    private static final int PORT = 8080;
    private static final String SERVICE = "integration-service";

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose-it.yml")
            .projectName(ProjectName.random())
            .waitingForService(SERVICE, HealthChecks.toHaveAllPortsOpen())
            .build();

    static DockerPort dockerPort;
    
    @BeforeClass
    public static void initialize() {
        dockerPort = docker.containers()
		                .container(SERVICE)
		                .port(PORT);
    }

    @Test
    public void insertOne() throws Exception {
        String endpoint = String.format("http://%s:%s", dockerPort.getIp(), dockerPort.getExternalPort());
        assertThat(this.template.getForObject(endpoint + "/", String.class)).contains("Hello World");
    }
}
