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

package org.apache.servicecomb.swagger.generator.protobuf.converter.model;

import org.apache.servicecomb.swagger.generator.protobuf.converter.AbstractProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.SwaggerToProtoGenerator;
import org.apache.servicecomb.swagger.generator.protobuf.converter.property.ArrayPropertyProtoConverter;

import io.swagger.models.ArrayModel;

public class ArrayModelProtoConverter extends AbstractProtoConverter {

  @Override
  protected String doParseToProto(SwaggerToProtoGenerator swaggerToClassGenerator, Object model) {
    ArrayModel arrayModel = (ArrayModel) model;

    if (arrayModel.getItems() != null) {
      String type = ArrayPropertyProtoConverter.parseToProtoMsg(swaggerToClassGenerator, arrayModel.getItems());
      return "repeated " + type;
    }

    // don't know when will this happen.
    throw new Error("not support null array model items.");
  }

}
