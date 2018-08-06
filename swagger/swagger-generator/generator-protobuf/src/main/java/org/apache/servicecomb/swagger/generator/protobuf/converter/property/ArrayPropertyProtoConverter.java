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

package org.apache.servicecomb.swagger.generator.protobuf.converter.property;

import org.apache.servicecomb.swagger.generator.protobuf.converter.AbstractProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.SwaggerToProtoGenerator;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;

public class ArrayPropertyProtoConverter extends AbstractProtoConverter {

  public static String parseToProtoMsg(SwaggerToProtoGenerator swaggerToClassGenerator,
      Property itemProperty) {
    return swaggerToClassGenerator.parse(itemProperty);
  }

  @Override protected String doParseToProto(SwaggerToProtoGenerator swaggerToClassGenerator, Object def) {

    ArrayProperty arrayProperty = (ArrayProperty) def;
    return "repeated " + parseToProtoMsg(swaggerToClassGenerator, arrayProperty.getItems());
  }

}
