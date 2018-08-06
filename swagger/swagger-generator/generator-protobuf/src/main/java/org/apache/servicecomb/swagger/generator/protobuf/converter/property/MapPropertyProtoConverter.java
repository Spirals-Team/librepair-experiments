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

import org.apache.servicecomb.swagger.generator.protobuf.converter.SwaggerToProtoGenerator;

import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.Property;

public class MapPropertyProtoConverter extends AbstractPropertyProtoConverter {

  private static String MAP_TEMPLATE = "map<String , %s>";

  @Override
  protected String doParseToProto(SwaggerToProtoGenerator swaggerToClassGenerator, Object property) {
    MapProperty mapProperty = (MapProperty) property;
    Property valueProperty = mapProperty.getAdditionalProperties();

    return parseToProtobuf(swaggerToClassGenerator, valueProperty);
  }


  public static String parseToProtobuf(SwaggerToProtoGenerator swaggerToClassGenerator,  Property valueProperty) {
    String mapNestType = swaggerToClassGenerator.parse(valueProperty);
    //这里查出底层的类型
    return String.format(MAP_TEMPLATE, mapNestType);
  }

}
