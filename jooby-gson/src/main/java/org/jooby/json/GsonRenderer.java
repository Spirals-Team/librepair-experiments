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
package org.jooby.json;

import static java.util.Objects.requireNonNull;

import org.jooby.MediaType;
import org.jooby.Renderer;

import com.google.gson.Gson;

class GsonRenderer implements Renderer {

  private MediaType type;

  private Gson gson;

  public GsonRenderer(final MediaType type, final Gson gson) {
    this.type = requireNonNull(type, "Media type is required.");

    this.gson = requireNonNull(gson, "Gson is required.");
  }

  @Override
  public void render(final Object object, final Context ctx) throws Exception {
    if (ctx.accepts(this.type)) {
      ctx.type(this.type)
          .send(gson.toJson(object));
    }
  }

  @Override
  public String name() {
    return "json";
  }

  @Override
  public String toString() {
    return name();
  }

}
