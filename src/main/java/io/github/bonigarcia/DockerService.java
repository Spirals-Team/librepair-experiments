/*
 * (C) Copyright 2017 Boni Garcia (http://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia;

import static io.github.bonigarcia.SeleniumJupiter.config;
import static java.lang.invoke.MethodHandles.lookup;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang.SystemUtils.IS_OS_MAC;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharStreams;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DefaultDockerClient.Builder;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;
import com.spotify.docker.client.messages.ProgressMessage;

/**
 * Docker Service.
 *
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.1.2
 */
public class DockerService {

    final Logger log = getLogger(lookup().lookupClass());

    private String dockerDefaultSocket;
    private int dockerWaitTimeoutSec;
    private int dockerPollTimeMs;
    private DockerClient dockerClient;

    public DockerService() throws DockerCertificateException {
        dockerDefaultSocket = config().getDockerDefaultSocket();
        dockerWaitTimeoutSec = config().getDockerWaitTimeoutSec();
        dockerPollTimeMs = config().getDockerPollTimeMs();

        Builder dockerClientBuilder = DefaultDockerClient.fromEnv();

        String dockerServerUrl = config().getDockerServerUrl();
        if (!dockerServerUrl.isEmpty()) {
            DefaultDockerClient.builder().uri(dockerServerUrl);
        }
        dockerClient = dockerClientBuilder.build();
    }

    public String getIpAddress(String containerId, String network)
            throws DockerException, InterruptedException, IOException {
        String dockerMachineIp = null;
        try {
            dockerMachineIp = getDockerMachineIp();
        } catch (Exception e) {
            log.trace("Docker machine not installed in this host");
        }
        if (dockerMachineIp == null
                || dockerMachineIp.contains("Host is not running")) {
            if (IS_OS_MAC) {
                dockerMachineIp = config().getDockerDefaultHost();
            } else {
                dockerMachineIp = dockerClient.inspectContainer(containerId)
                        .networkSettings().networks().get(network).gateway();
            }
        }
        return dockerMachineIp;
    }

    public String startContainer(DockerContainer dockerContainer)
            throws DockerException, InterruptedException {
        String imageId = dockerContainer.getImageId();
        log.info("Starting Docker container {}", imageId);
        com.spotify.docker.client.messages.HostConfig.Builder hostConfigBuilder = HostConfig
                .builder();
        com.spotify.docker.client.messages.ContainerConfig.Builder containerConfigBuilder = ContainerConfig
                .builder();

        Optional<String> network = dockerContainer.getNetwork();
        if (network.isPresent()) {
            log.trace("Using network: {}", network.get());
            hostConfigBuilder.networkMode(network.get());
        }
        Optional<Map<String, List<PortBinding>>> portBindings = dockerContainer
                .getPortBindings();
        if (portBindings.isPresent()) {
            log.trace("Using port bindings: {}", portBindings.get());
            hostConfigBuilder.portBindings(portBindings.get());
            containerConfigBuilder.exposedPorts(portBindings.get().keySet());
        }
        Optional<List<String>> binds = dockerContainer.getBinds();
        if (binds.isPresent()) {
            log.trace("Using binds: {}", binds.get());
            hostConfigBuilder.binds(binds.get());
        }
        Optional<List<String>> envs = dockerContainer.getEnvs();
        if (envs.isPresent()) {
            log.trace("Using envs: {}", envs.get());
            containerConfigBuilder.env(envs.get());
        }
        Optional<List<String>> cmd = dockerContainer.getCmd();
        if (cmd.isPresent()) {
            log.trace("Using cmd: {}", cmd.get());
            containerConfigBuilder.cmd(cmd.get());
        }
        Optional<List<String>> entryPoint = dockerContainer.getEntryPoint();
        if (entryPoint.isPresent()) {
            log.trace("Using entryPoint: {}", entryPoint.get());
            containerConfigBuilder.entrypoint(entryPoint.get());
        }

        ContainerConfig createContainer = containerConfigBuilder.image(imageId)
                .hostConfig(hostConfigBuilder.build()).build();
        String containerId = dockerClient.createContainer(createContainer).id();
        dockerClient.startContainer(containerId);

        return containerId;
    }

    public String getBindPort(String containerId, String exposed)
            throws DockerException, InterruptedException {
        ImmutableMap<String, List<PortBinding>> ports = dockerClient
                .inspectContainer(containerId).networkSettings().ports();
        return ports.get(exposed).get(0).hostPort();
    }

    public void pullImage(String imageId)
            throws DockerException, InterruptedException {
        log.info("Pulling Docker image {} ... please wait", imageId);
        dockerClient.pull(imageId, new ProgressHandler() {
            @Override
            public void progress(ProgressMessage message)
                    throws DockerException {
                log.trace("Pulling Docker image {} ... {}", imageId, message);
            }
        });
        log.trace("Docker image {} downloaded", imageId);
    }

    public void pullImageIfNecessary(String imageId)
            throws DockerException, InterruptedException {
        if (!existsImage(imageId)) {
            pullImage(imageId);
        }
    }

    public boolean existsImage(String imageId) {
        boolean exists = true;
        try {
            dockerClient.inspectImage(imageId);
            log.trace("Docker image {} already exists", imageId);

        } catch (Exception e) {
            log.trace("Image {} does not exist", imageId);
            exists = false;
        }
        return exists;
    }

    public void stopAndRemoveContainer(String containerId, String imageId) {
        log.info("Stopping Docker container {}", imageId);
        try {
            stopContainer(containerId);
            removeContainer(containerId);
        } catch (Exception e) {
            log.warn("Exception stopping container {}", imageId, e);
        }
    }

    public void stopContainer(String containerId)
            throws DockerException, InterruptedException {
        int stopTimeoutSec = config().getDockerStopTimeoutSec();
        log.trace("Stopping container {} (timeout {} seconds)", containerId,
                stopTimeoutSec);
        dockerClient.stopContainer(containerId, stopTimeoutSec);
    }

    public void removeContainer(String containerId)
            throws DockerException, InterruptedException {
        log.trace("Removing container {}", containerId);
        dockerClient.removeContainer(containerId);
    }

    public String getDockerMachineIp() throws IOException {
        return runAndWait("docker-machine", "ip");
    }

    public String runAndWait(String... command) throws IOException {
        Process process = new ProcessBuilder(command).redirectErrorStream(true)
                .start();
        String result = CharStreams
                .toString(
                        new InputStreamReader(process.getInputStream(), UTF_8))
                .replaceAll("\\r", "").replaceAll("\\n", "");
        process.destroy();
        if (log.isTraceEnabled()) {
            log.trace("Running command on the shell: {} -- result: {}",
                    Arrays.toString(command), result);
        }
        return result;
    }

    public String getDockerDefaultSocket() {
        return dockerDefaultSocket;
    }

    public int getDockerWaitTimeoutSec() {
        return dockerWaitTimeoutSec;
    }

    public int getDockerPollTimeMs() {
        return dockerPollTimeMs;
    }

    public void close() {
        dockerClient.close();
    }

}
