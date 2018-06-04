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

package org.apache.servicecomb.transport.rest.vertx.accesslog.parser.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.servicecomb.transport.rest.vertx.accesslog.element.AccessLogItem;
import org.apache.servicecomb.transport.rest.vertx.accesslog.element.impl.UserDefinedAccessLogItem;
import org.apache.servicecomb.transport.rest.vertx.accesslog.element.impl.UserDefinedAccessLogItemHighPriority;
import org.apache.servicecomb.transport.rest.vertx.accesslog.parser.AccessLogItemMeta;
import org.apache.servicecomb.transport.rest.vertx.accesslog.parser.VertxRestAccessLogItemCreator;

import io.vertx.ext.web.RoutingContext;

/**
 * For access log extension test
 */
public class UserDefinedAccessLogItemCreator implements VertxRestAccessLogItemCreator {
  private static final List<AccessLogItemMeta> supportedAccessLogItemMeta = new ArrayList<>();

  public UserDefinedAccessLogItemCreator() {
    supportedAccessLogItemMeta.add(
        new AccessLogItemMeta("%{", "}user-defined"));
    supportedAccessLogItemMeta.add(
        new AccessLogItemMeta("%h", null, -1));
  }

  @Override
  public List<AccessLogItemMeta> getAccessLogItemMeta() {
    return supportedAccessLogItemMeta;
  }

  @Override
  public AccessLogItem<RoutingContext> createItem(AccessLogItemMeta accessLogItemMeta, String config) {
    if (null == accessLogItemMeta.getSuffix()) {
      return new UserDefinedAccessLogItemHighPriority();
    }
    if ("}user-defined".equals(accessLogItemMeta.getSuffix())) {
      return new UserDefinedAccessLogItem(config);
    }
    return null;
  }
}