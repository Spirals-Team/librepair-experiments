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
package org.apache.servicecomb.swagger.generator.core.processor.response;

import java.lang.reflect.Type;

import org.apache.servicecomb.swagger.generator.core.OperationGenerator;
import org.apache.servicecomb.swagger.generator.core.ResponseTypeProcessor;
import org.apache.servicecomb.swagger.generator.core.utils.ParamUtils;

import io.swagger.converter.ModelConverters;
import io.swagger.models.properties.Property;

public class DefaultResponseTypeProcessor implements ResponseTypeProcessor {
  @Override
  public Class<?> getResponseType() {
    // not care for this.
    return null;
  }

  @Override
  public Property process(OperationGenerator operationGenerator) {
    Type responseType = operationGenerator.getProviderMethod().getGenericReturnType();

    ParamUtils.addDefinitions(operationGenerator.getSwagger(), responseType);
    return ModelConverters.getInstance().readAsProperty(responseType);
  }
}
