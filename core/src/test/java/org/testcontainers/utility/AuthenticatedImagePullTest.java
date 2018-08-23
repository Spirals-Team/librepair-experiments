package org.testcontainers.utility;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.AuthConfig;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.startupcheck.OneShotStartupCheckStrategy;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * This test checks the integration between Testcontainers and an authenticated registry, but uses
 * a mock instance of {@link RegistryAuthLocator} - the purpose of the test is solely to ensure that
 * the auth locator is utilised, and that the credentials it provides flow through to the registry.
 *
 * {@link RegistryAuthLocatorTest} covers actual credential scenarios at a lower level, which are
 * impractical to test end-to-end.
 */
public class AuthenticatedImagePullTest {

    @ClassRule
    public static GenericContainer authenticatedRegistry = new GenericContainer(new ImageFromDockerfile()
        .withDockerfileFromBuilder(builder -> {
            builder.from("registry:2")
                .run("htpasswd -Bbn testuser notasecret > /htpasswd")
                .env("REGISTRY_AUTH", "htpasswd")
                .env("REGISTRY_AUTH_HTPASSWD_PATH", "/htpasswd")
                .env("REGISTRY_AUTH_HTPASSWD_REALM", "Test");
        }))
        .withExposedPorts(5000)
        .waitingFor(new HttpWaitStrategy());

    private static RegistryAuthLocator originalAuthLocatorSingleton;
    private static DockerClient client;

    private static String testRegistryAddress;
    private static String testImageName;
    private static String testImageNameWithTag;

    @BeforeClass
    public static void setup() {
        originalAuthLocatorSingleton = RegistryAuthLocator.instance();
        client = DockerClientFactory.instance().client();

        testRegistryAddress = authenticatedRegistry.getContainerIpAddress() + ":" + authenticatedRegistry.getFirstMappedPort();
        testImageName = testRegistryAddress + "/alpine";
        testImageNameWithTag = testImageName + ":latest";
    }

    @AfterClass
    public static void cleanup() {
        RegistryAuthLocator.setInstance(originalAuthLocatorSingleton);
        client.removeImageCmd(testImageNameWithTag).withForce(true).exec();
    }

    @Test
    public void testThatAuthLocatorIsUsed() throws Exception {

        final DockerImageName expectedName = new DockerImageName(testImageNameWithTag);
        final AuthConfig authConfig = new AuthConfig()
            .withUsername("testuser")
            .withPassword("notasecret")
            .withRegistryAddress("http://" + testRegistryAddress);

        // Replace the RegistryAuthLocator singleton with our mock, for the duration of this test
        final RegistryAuthLocator mockAuthLocator = Mockito.mock(RegistryAuthLocator.class);
        RegistryAuthLocator.setInstance(mockAuthLocator);
        when(mockAuthLocator.lookupAuthConfig(eq(expectedName), any()))
            .thenReturn(authConfig);

        // a push will use the auth locator for authentication, although that isn't the goal of this test
        putImageInRegistry();

        // actually start a container, which will require an authenticated pull
        try (final GenericContainer container = new GenericContainer<>(testImageNameWithTag)
            .withCommand("/bin/sh", "-c", "sleep 0")
            .withStartupCheckStrategy(new OneShotStartupCheckStrategy())) {
            container.start();
            // do nothing other than start and stop
        }
    }

    private void putImageInRegistry() throws InterruptedException {
        client.pullImageCmd("alpine:latest")
            .exec(new PullImageResultCallback())
            .awaitCompletion(1, TimeUnit.MINUTES);

        final String id = client.listImagesCmd()
            .withImageNameFilter("alpine:latest")
            .exec()
            .get(0)
            .getId();

        // push the image to the registry
        client.tagImageCmd(id, testImageName, "latest").exec();

        client.pushImageCmd(testImageNameWithTag)
            .exec(new PushImageResultCallback())
            .awaitCompletion(1, TimeUnit.MINUTES);

        // remove the image tag from local docker so that it must be pulled before use
        client.removeImageCmd(testImageNameWithTag).withForce(true).exec();
    }
}
