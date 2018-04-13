package guru.bonacci.oogway.gateway.services;

import static com.palantir.docker.compose.logging.LogDirectory.circleAwareLogDirectory;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.configuration.ProjectName;
import com.palantir.docker.compose.connection.DockerPort;
import com.palantir.docker.compose.connection.waiting.HealthChecks;

import guru.bonacci.oogway.gateway.GatewayTestApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayTestApp.class, properties = {
	"spring.cloud.config.enabled=false",
	"eureka.client.enabled=false"
})
@Ignore
public class GatewayControllerTests {

	private TestRestTemplate template = new TestRestTemplate();
	
    private static final int PORT = 8080;
    private static final String SERVICE = "gateway-service";

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
            .file("src/test/resources/docker-compose-it.yml")
            .saveLogsTo(circleAwareLogDirectory(GatewayControllerTests.class))
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
        assertThat(this.template.getForObject(endpoint + "/version", String.class)).contains("Hello World");
    }
}
