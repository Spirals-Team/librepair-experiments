package com.github.dockerjava.junit;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.PullImageResultCallback;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @author Kanstantsin Shautsou
 */
public class DockerRule extends ExternalResource {
    public static final Logger LOG = LoggerFactory.getLogger(DockerRule.class);
    public static final String DEFAULT_IMAGE = "busubox:latest";

    private DockerCmdExecFactory cmdExecFactory;
    private DockerClient client;


    public DockerRule(DockerCmdExecFactory cmdExecFactory) {
        this.cmdExecFactory = cmdExecFactory;
    }

    public DockerCmdExecFactory getCmdExecFactory() {
        return cmdExecFactory;
    }

    public DockerClient getClient() {
        return client;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return super.apply(base, description);
    }

    @Override
    protected void before() throws Throwable {

        LOG.info("======================= BEFORETEST =======================");
        LOG.info("Connecting to Docker server");
        client = DockerClientBuilder.getInstance(config())
                .withDockerCmdExecFactory(getCmdExecFactory())
                .build();

        try {
            client.inspectImageCmd("busybox").exec();
        } catch (NotFoundException e) {
            LOG.info("Pulling image 'busybox'");
            // need to block until image is pulled completely
            client.pullImageCmd("busybox").withTag("latest").exec(new PullImageResultCallback()).awaitSuccess();
        }

        assertThat(client, notNullValue());
        LOG.info("======================= END OF BEFORETEST =======================\n\n");
    }

    @Override
    protected void after() {
        LOG.debug("======================= END OF AFTERTEST =======================");
    }

    private DefaultDockerClientConfig config() {
        return config(null);
    }

    protected DefaultDockerClientConfig config(String password) {
        DefaultDockerClientConfig.Builder builder = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withRegistryUrl("https://index.docker.io/v1/");
        if (password != null) {
            builder = builder.withRegistryPassword(password);
        }

        return builder.build();
    }
}
