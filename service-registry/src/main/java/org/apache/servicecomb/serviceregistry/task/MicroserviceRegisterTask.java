/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.servicecomb.serviceregistry.task;

import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.core.Response.Status;

import org.apache.servicecomb.foundation.common.base.ServiceCombConstants;
import org.apache.servicecomb.serviceregistry.api.registry.Microservice;
import org.apache.servicecomb.serviceregistry.api.response.GetSchemaResponse;
import org.apache.servicecomb.serviceregistry.client.ServiceRegistryClient;
import org.apache.servicecomb.serviceregistry.client.http.Holder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.common.base.Charsets;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.hash.Hashing;

public class MicroserviceRegisterTask extends AbstractRegisterTask {
  private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceRegisterTask.class);

  private boolean schemaIdSetMatch;

  public MicroserviceRegisterTask(EventBus eventBus, ServiceRegistryClient srClient, Microservice microservice) {
    super(eventBus, srClient, microservice);
    this.taskStatus = TaskStatus.READY;
  }

  public boolean isSchemaIdSetMatch() {
    return schemaIdSetMatch;
  }

  @Subscribe
  public void onMicroserviceInstanceHeartbeatTask(MicroserviceInstanceHeartbeatTask task) {
    if (task.getHeartbeatResult() != HeartbeatResult.SUCCESS && isSameMicroservice(task.getMicroservice())) {
      LOGGER.info("read MicroserviceInstanceHeartbeatTask status is {}", task.taskStatus);
      this.taskStatus = TaskStatus.READY;
      this.registered = false;
    }
  }

  @Subscribe
  public void onInstanceRegistryFailed(MicroserviceInstanceRegisterTask task) {
    if (task.taskStatus != TaskStatus.FINISHED) {
      LOGGER.info("read MicroserviceInstanceRegisterTask status is {}", task.taskStatus);
      this.taskStatus = TaskStatus.READY;
      this.registered = false;
    }
  }

  @Override
  protected boolean doRegister() {
    LOGGER.info("running microservice register task.");
    String serviceId = srClient.getMicroserviceId(microservice.getAppId(),
        microservice.getServiceName(),
        microservice.getVersion(),
        microservice.getEnvironment());
    if (!StringUtils.isEmpty(serviceId)) {
      // This microservice has been registered, so we just use the serviceId gotten from service center
      microservice.setServiceId(serviceId);
      LOGGER.info(
          "Microservice exists in service center, no need to register. id=[{}] appId=[{}], name=[{}], version=[{}], env=[{}]",
          serviceId,
          microservice.getAppId(),
          microservice.getServiceName(),
          microservice.getVersion(),
          microservice.getEnvironment());

      if (!checkSchemaIdSet()) {
        return false;
      }
    } else {
      serviceId = srClient.registerMicroservice(microservice);
      if (StringUtils.isEmpty(serviceId)) {
        LOGGER.error(
            "Registry microservice failed. appId=[{}], name=[{}], version=[{}], env=[{}]",
            microservice.getAppId(),
            microservice.getServiceName(),
            microservice.getVersion(),
            microservice.getEnvironment());
        return false;
      }

      // In re-register microservice case, the old instanceId should not be cached
      microservice.getInstance().setInstanceId(null);

      LOGGER.info(
          "Registry Microservice successfully. id=[{}] appId=[{}], name=[{}], version=[{}], schemaIds={}, env=[{}]",
          serviceId,
          microservice.getAppId(),
          microservice.getServiceName(),
          microservice.getVersion(),
          microservice.getSchemas(),
          microservice.getEnvironment());
    }

    microservice.setServiceId(serviceId);
    microservice.getInstance().setServiceId(microservice.getServiceId());

    return registerSchemas();
  }

  private boolean checkSchemaIdSet() {
    Microservice existMicroservice = srClient.getMicroservice(microservice.getServiceId());
    if (existMicroservice == null) {
      LOGGER.error("Error to get microservice from service center when check schema set");
      return false;
    }
    Set<String> existSchemas = new HashSet<>(existMicroservice.getSchemas());
    Set<String> localSchemas = new HashSet<>(microservice.getSchemas());
    schemaIdSetMatch = existSchemas.equals(localSchemas);

    if (!schemaIdSetMatch) {
      LOGGER.warn(
          "SchemaIds is different between local and service center. "
              + "serviceId=[{}] appId=[{}], name=[{}], version=[{}], env=[{}], local schemaIds={}, service center schemaIds={}",
          microservice.getServiceId(),
          microservice.getAppId(),
          microservice.getServiceName(),
          microservice.getVersion(),
          microservice.getEnvironment(),
          localSchemas,
          existSchemas);
      return true;
    }

    LOGGER.info(
        "SchemaIds are equals to service center. serviceId=[{}], appId=[{}], name=[{}], version=[{}], env=[{}], schemaIds={}",
        microservice.getServiceId(),
        microservice.getAppId(),
        microservice.getServiceName(),
        microservice.getVersion(),
        microservice.getEnvironment(),
        localSchemas);
    return true;
  }

  private boolean registerSchemas() {
    Holder<List<GetSchemaResponse>> onlineSchemaHolder = srClient.getSchemas(microservice.getServiceId());
    if (Status.OK.getStatusCode() != onlineSchemaHolder.getStatusCode()) {
      LOGGER.error("failed to get schemas from service center, statusCode = [{}]", onlineSchemaHolder.getStatusCode());
      return false;
    }

    boolean registerSuccess = true;
    List<GetSchemaResponse> onlineSchemaList = onlineSchemaHolder.getValue();
    Set<String> onlineSchemaIdSet = extractOnlineSchemaIds(onlineSchemaList);
    for (Entry<String, String> entry : microservice.getSchemaMap().entrySet()) {
      if (!registerSuccess) {
        break;
      }

      String localSchemaId = entry.getKey();
      String localSchemaContent = entry.getValue();
      GetSchemaResponse onlineSchema = extractSchema(localSchemaId, onlineSchemaList);
      boolean onlineSchemaExists = onlineSchema != null;

      LOGGER.info("schemaId [{}] exists [{}], summary exists [{}]", localSchemaId, onlineSchemaExists,
          onlineSchema != null && onlineSchema.getSummary() != null);
      if (!onlineSchemaExists) {
        // The ids of schemas are contained by microservice registry request, which means once a microservice
        // is registered in the service center, the schemas that it contains are determined.
        // If we get a microservice but cannot find the given schemaId in it's schemaId list, this means that
        // the schemas of this microservice has been changed, and we should decide whether to register this new
        // schema according to it's environment configuration.
        if (onlineSchemaIsModifiable()) {
          registerSuccess = registerSingleSchema(localSchemaId, localSchemaContent);
          continue;
        }

        throw new IllegalStateException("There is a schema only existing in local microservice: [" + localSchemaId
            + "], which means there are interfaces changed. "
            + "You need to increment microservice version before deploying, "
            + "or you can configure service_description.environment="
            + ServiceCombConstants.DEVELOPMENT_SERVICECOMB_ENV
            + " to work in development environment and ignore this error");
      }

      onlineSchemaIdSet.remove(onlineSchema.getSchemaId());

      String onlineSchemaSummary = onlineSchema.getSummary();
      if (null == onlineSchemaSummary) {
        // if there is no online summery, query online schema content directly and calculate summary
        String onlineSchemaContent = srClient.getSchema(microservice.getServiceId(), localSchemaId);

        if (null == onlineSchemaContent) {
          // online content is also null, register schema directly
          registerSuccess = registerSingleSchema(localSchemaId, localSchemaContent);
          continue;
        }

        onlineSchemaSummary = Hashing.sha256().newHasher()
            .putString(onlineSchemaContent, Charsets.UTF_8).hash().toString();
      }

      String localSchemaSummary = Hashing.sha256().newHasher().putString(localSchemaContent, Charsets.UTF_8).hash()
          .toString();
      if (!localSchemaSummary.equals(onlineSchemaSummary)) {
        if (onlineSchemaIsModifiable()) {
          LOGGER.info(
              "schema[{}]'s content is changed and the current environment is [{}], so re-register it!",
              localSchemaId, ServiceCombConstants.DEVELOPMENT_SERVICECOMB_ENV);
          registerSuccess = registerSingleSchema(localSchemaId, localSchemaContent);
          continue;
        }

        // env is not development, throw an exception and break the init procedure
        throw new IllegalStateException(
            "The schema(id=[" + localSchemaId + "]) content held by this instance and the service center is different. "
                + "You need to increment microservice version before deploying. "
                + "Or you can configure service_description.environment="
                + ServiceCombConstants.DEVELOPMENT_SERVICECOMB_ENV
                + " to work in development environment and ignore this error");
      }
    }

    if (registerSuccess && !onlineSchemaIdSet.isEmpty()) {
      // there are some schemas only exist in service center
      if (!onlineSchemaIsModifiable()) {
        // env is not development, throw an exception and break the init procedure
        throw new IllegalStateException("There are schemas only existing in service center: " + onlineSchemaIdSet
            + ", which means there are interfaces changed. "
            + "You need to increment microservice version before deploying, "
            + "or you can configure service_description.environment="
            + ServiceCombConstants.DEVELOPMENT_SERVICECOMB_ENV
            + " to work in development environment and ignore this error");
      }

      // Currently nothing to do but print a warning
      LOGGER.warn("There are schemas only existing in service center: {}, which means there are interfaces changed. "
          + "It's recommended to increment microservice version before deploying.", onlineSchemaIdSet);
    }

    schemaIdSetMatch = registerSuccess;
    return registerSuccess;
  }

  private boolean onlineSchemaIsModifiable() {
    return ServiceCombConstants.DEVELOPMENT_SERVICECOMB_ENV.equalsIgnoreCase(microservice.getEnvironment());
  }

  /**
   * Register a schema directly.
   * @return true if register success, otherwise false
   */
  private boolean registerSingleSchema(String schemaId, String content) {
    return srClient.registerSchema(microservice.getServiceId(), schemaId, content);
  }

  /**
   * Select schema response by schemaId from query response list
   * @param schemaId the select key schemaId
   * @param schemas a list of schema response queried from service center
   * @return a schema response match the schemaId, otherwise null
   */
  private GetSchemaResponse extractSchema(String schemaId, List<GetSchemaResponse> schemas) {
    if (schemas == null || schemas.isEmpty()) {
      return null;
    }
    GetSchemaResponse schema = null;
    for (GetSchemaResponse tempSchema : schemas) {
      if (tempSchema.getSchemaId().equals(schemaId)) {
        schema = tempSchema;
        break;
      }
    }
    return schema;
  }

  private Set<String> extractOnlineSchemaIds(List<GetSchemaResponse> existSchemas) {
    Set<String> onlineSchemaSet = new HashSet<>();
    if (null == existSchemas) {
      return onlineSchemaSet;
    }

    for (GetSchemaResponse schema : existSchemas) {
      onlineSchemaSet.add(schema.getSchemaId());
    }
    return onlineSchemaSet;
  }
}
