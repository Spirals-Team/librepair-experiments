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
package org.jooby.internal.spec;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.MoreObjects;

public class LocalStack {

  private Map<String, Type> vars = new LinkedHashMap<>();

  private LocalStack parent;

  public LocalStack(final LocalStack parent) {
    this.parent = parent;
  }

  public Type get(final String name) {
    Type type = vars.get(name);
    if (type == null && parent != null) {
      type = parent.get(name);
    }
    return type;
  }

  public void put(final String name, final Type type) {
    vars.put(name, type);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .addValue(vars)
        .add("parent", parent)
        .toString();
  }

}
