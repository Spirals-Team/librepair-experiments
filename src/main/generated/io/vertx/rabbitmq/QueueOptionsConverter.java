/*
 * Copyright (c) 2014 Red Hat, Inc. and others
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.rabbitmq;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link io.vertx.rabbitmq.QueueOptions}.
 *
 * NOTE: This class has been automatically generated from the {@link io.vertx.rabbitmq.QueueOptions} original class using Vert.x codegen.
 */
public class QueueOptionsConverter {

  public static void fromJson(JsonObject json, QueueOptions obj) {
    if (json.getValue("autoAck") instanceof Boolean) {
      obj.setAutoAck((Boolean)json.getValue("autoAck"));
    }
    if (json.getValue("buffer") instanceof Boolean) {
      obj.setBuffer((Boolean)json.getValue("buffer"));
    }
    if (json.getValue("keepMostRecent") instanceof Boolean) {
      obj.setKeepMostRecent((Boolean)json.getValue("keepMostRecent"));
    }
    if (json.getValue("maxInternalQueueSize") instanceof Number) {
      obj.setMaxInternalQueueSize(((Number)json.getValue("maxInternalQueueSize")).intValue());
    }
  }

  public static void toJson(QueueOptions obj, JsonObject json) {
    json.put("autoAck", obj.isAutoAck());
    json.put("buffer", obj.isBuffer());
    json.put("keepMostRecent", obj.isKeepMostRecent());
  }
}