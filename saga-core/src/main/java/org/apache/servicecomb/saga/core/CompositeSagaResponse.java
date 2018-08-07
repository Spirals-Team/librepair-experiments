/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.saga.core;

import java.util.Collection;
import java.util.Optional;

public class CompositeSagaResponse implements SagaResponse {
  private final Collection<SagaResponse> responses;

  public CompositeSagaResponse(Collection<SagaResponse> responses) {
    this.responses = responses;
  }

  @Override
  public boolean succeeded() {
    return responses.stream().allMatch(SagaResponse::succeeded);
  }

  @Override
  public String body() {
    Optional<String> reduce = responses.stream()
        .map(SagaResponse::body)
        .reduce((a, b) -> a + ", " + b)
        .map(combined -> "[" + combined + "]");

    return reduce.orElse("{}");
  }

  public Collection<SagaResponse> responses() {
    return responses;
  }
}
