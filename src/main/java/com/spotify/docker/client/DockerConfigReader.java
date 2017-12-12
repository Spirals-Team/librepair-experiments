/*-
 * -\-\-
 * docker-client
 * --
 * Copyright (C) 2016 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.docker.client;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import com.spotify.docker.client.messages.RegistryAuth;
import com.spotify.docker.client.messages.RegistryConfigs;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DockerConfigReader {
  private static final Logger LOG = LoggerFactory.getLogger(DockerConfigReader.class);

  private static final ObjectMapper MAPPER = ObjectMapperProvider.objectMapper();

  /** Returns all RegistryConfig instances from the configuration file. */
  public RegistryConfigs fromConfig(final Path configPath) throws IOException {
    return parseDockerConfig(configPath);
  }

  /**
   * Returns the RegistryAuth for the config file for the given registry server name.
   *
   * @throws IllegalArgumentException if the config file does not contain registry auth info for the
   *                                  registry
   */
  public RegistryAuth fromConfig(final Path configPath, final String serverAddress)
      throws IOException {
    return parseDockerConfig(configPath, serverAddress);
  }

  /**
   * @deprecated do not use - only exists for backwards compatibility. Use {@link #fromConfig(Path)}
   *     instead.
   */
  @Deprecated
  public RegistryAuth fromFirstConfig(Path configPath) throws IOException {
    return parseDockerConfig(configPath, null);
  }

  private RegistryAuth parseDockerConfig(final Path configPath, final String serverAddress)
      throws IOException {
    checkNotNull(configPath);

    final Map<String, RegistryAuth> configs = parseDockerConfig(configPath).configs();

    if (isNullOrEmpty(serverAddress)) {
      if (configs.isEmpty()) {
        return RegistryAuth.builder().build();
      }
      LOG.warn("Returning first entry from docker config file - use fromConfig(Path) instead, "
               + "this behavior is deprecated and will soon be removed");
      return configs.values().iterator().next();
    }

    if (configs.containsKey(serverAddress)) {
      return configs.get(serverAddress);
    }

    // If the given server address didn't have a protocol try adding a protocol to the address.
    // This handles cases where older versions of Docker included the protocol when writing
    // auth tokens to config.json.
    try {
      final URI serverAddressUri = new URI(serverAddress);
      if (serverAddressUri.getScheme() == null) {
        for (String proto : Arrays.asList("https://", "http://")) {
          final String addrWithProto = proto + serverAddress;
          if (configs.containsKey(addrWithProto)) {
            return configs.get(addrWithProto);
          }
        }
      }
    } catch (URISyntaxException e) {
      // Nothing to do, just let this fall through below
    }

    throw new IllegalArgumentException(
        "serverAddress=" + serverAddress + " does not appear in config file at " + configPath);
  }

  private RegistryConfigs parseDockerConfig(final Path configPath) throws IOException {
    checkNotNull(configPath);
    return MAPPER.treeToValue(extractAuthJson(configPath), RegistryConfigs.class);
  }

  public Path defaultConfigPath() {
    final String home = System.getProperty("user.home");
    final Path dockerConfig = Paths.get(home, ".docker", "config.json");
    final Path dockerCfg = Paths.get(home, ".dockercfg");

    if (Files.exists(dockerConfig)) {
      LOG.debug("Using configfile: {}", dockerConfig);
      return dockerConfig;
    } else {
      LOG.debug("Using configfile: {} ", dockerCfg);
      return dockerCfg;
    }
  }

  private ObjectNode extractAuthJson(final Path configPath) throws IOException {
    final File file = configPath.toFile();

    final JsonNode config = MAPPER.readTree(file);

    Preconditions.checkState(config.isObject(),
        "config file contents are not a JSON Object, instead it is a %s", config.getNodeType());

    if (config.has("auths")) {
      final JsonNode auths = config.get("auths");
      Preconditions.checkState(auths.isObject(),
          "config file contents are not a JSON Object, instead it is a %s", auths.getNodeType());
      return (ObjectNode) auths;
    }

    return (ObjectNode) config;
  }
}
