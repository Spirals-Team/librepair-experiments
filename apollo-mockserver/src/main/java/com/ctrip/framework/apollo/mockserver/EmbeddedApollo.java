package com.ctrip.framework.apollo.mockserver;

import com.ctrip.framework.apollo.core.dto.ApolloConfig;
import com.ctrip.framework.apollo.core.dto.ApolloConfigNotification;
import com.ctrip.framework.apollo.core.dto.ServiceDTO;
import com.ctrip.framework.apollo.core.utils.ResourceUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create by zhangzheng on 8/22/18 Email:zhangzheng@youzan.com
 */
public class EmbeddedApollo extends ExternalResource {
  private static final Logger logger = LoggerFactory.getLogger(EmbeddedApollo.class);
  private static final Type notificationType = new TypeToken<List<ApolloConfigNotification>>() {
  }.getType();

  private final Gson gson = new Gson();
  private final Map<String, Map<String, String>> overriddenPropertiesOfNamespace = new HashMap<>();
  private final Map<String, Set<String>> deletedKeysOfNamespace = new HashMap<>();

  private String listeningUrl;
  private MockWebServer server;

  @Override
  protected void before() throws Throwable {
    server = new MockWebServer();
    final Dispatcher dispatcher = new Dispatcher() {
      @Override
      public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        if (request.getPath().startsWith("/services/config")) {

          return new MockResponse().setResponseCode(200).setBody(mockConfigServiceAddress(listeningUrl));
        } else if (request.getPath().startsWith("/notifications/v2")) {
          String notifications = request.getRequestUrl().queryParameter("notifications");
          return new MockResponse().setResponseCode(200).setBody(mockLongPollBody(notifications));
        } else if (request.getPath().startsWith("/configs")) {
          List<String> pathSegments = request.getRequestUrl().pathSegments();
          // appId and cluster might be used in the future
          String appId = pathSegments.get(1);
          String cluster = pathSegments.get(2);
          String namespace = pathSegments.get(3);
          return new MockResponse().setResponseCode(200).setBody(loadConfigFor(namespace));
        }
        return new MockResponse().setResponseCode(404);
      }
    };

    server.setDispatcher(dispatcher);
    server.start();
    //指定apollo的meta server地址为localhost
    int port = server.getPort();
    this.listeningUrl = "http://localhost:" + port;

    MockedMetaServerProvider.setAddress(listeningUrl);

    System.setProperty("apollo.longPollingInitialDelayInMills", "1");

    super.before();
  }

  @Override
  protected void after() {
    try {
      server.close();
    } catch (IOException e) {
      logger.error("stop apollo server error", e);
    }
  }


  private String loadConfigFor(String namespace) {
    String filename = String.format("mockdata-%s.properties", namespace);
    final Properties prop = ResourceUtils.readConfigFile(filename, new Properties());
    Map<String, String> configurations = prop.stringPropertyNames().stream().collect(
        Collectors.toMap(key -> key, prop::getProperty));
    ApolloConfig apolloConfig = new ApolloConfig("someAppId", "someCluster", namespace, "someReleaseKey");

    Map<String, String> mergedConfigurations = mergeOverriddenProperties(namespace, configurations);
    apolloConfig.setConfigurations(mergedConfigurations);
    return gson.toJson(apolloConfig);
  }


  private String mockLongPollBody(String notificationsStr) {
    List<ApolloConfigNotification> oldNotifications = gson.fromJson(notificationsStr, notificationType);
    List<ApolloConfigNotification> newNotifications = new ArrayList<>();
    for (ApolloConfigNotification notification : oldNotifications) {
      newNotifications.add(new ApolloConfigNotification(notification.getNamespaceName(), notification.getNotificationId() + 1));
    }
    return gson.toJson(newNotifications);
  }

  private String mockConfigServiceAddress(String address) {
    ServiceDTO serviceDTO = new ServiceDTO();
    serviceDTO.setAppName("someAppName");
    serviceDTO.setInstanceId("someInstanceId");
    serviceDTO.setHomepageUrl(address);
    return gson.toJson(Collections.singletonList(serviceDTO));
  }

  /**
   * 合并用户对namespace的修改
   */
  private Map<String, String> mergeOverriddenProperties(String namespace, Map<String, String> configurations) {
    if (overriddenPropertiesOfNamespace.containsKey(namespace)) {
      configurations.putAll(overriddenPropertiesOfNamespace.get(namespace));
    }
    if (deletedKeysOfNamespace.containsKey(namespace)) {
      for (String k : deletedKeysOfNamespace.get(namespace)) {
        configurations.remove(k);
      }
    }
    return configurations;
  }

  public void addOrModifyProperty(String namespace, String someKey, String someValue) {
    if (overriddenPropertiesOfNamespace.containsKey(namespace)) {
      overriddenPropertiesOfNamespace.get(namespace).put(someKey, someValue);
    } else {
      overriddenPropertiesOfNamespace.put(namespace, ImmutableMap.of(someKey, someValue));
    }
  }

  public void deleteProperty(String namespace, String someKey) {
    if (deletedKeysOfNamespace.containsKey(namespace)) {
      deletedKeysOfNamespace.get(namespace).add(someKey);
    } else {
      deletedKeysOfNamespace.put(namespace, ImmutableSet.of(someKey));
    }
  }

}
