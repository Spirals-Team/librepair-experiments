/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jooby.spec;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

/**
 * Represent a route response.
 *
 * @author edgar
 * @since 0.15.0
 */
public interface RouteResponse extends Serializable {

  /**
   * @return Route return type.
   */
  Type type();

  /**
   * @return Route return doc.
   */
  Optional<String> doc();

  /**
   * @return Default status code.
   */
  default int statusCode() {
    return statusCodes().entrySet().stream()
        .map(it -> it.getKey())
        .filter(code -> code >= 200 && code < 400)
        .findFirst()
        .orElseGet(() -> type() == void.class ? 204 : 200);
  }

  /**
   * @return Map of status code responses.
   */
  Map<Integer, String> statusCodes();
}
