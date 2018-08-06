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
import org.apache.servicecomb.swagger.generator.protobuf.converter.ProtobufConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.SwaggerToProtoGenerator;
import org.apache.servicecomb.swagger.generator.protobuf.converter.property.MapPropertyProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.protoModel.FieldModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.models.ModelImpl;
import io.swagger.models.properties.ObjectProperty;

public class ModelImplProtoConverter extends AbstractProtoConverter {
  private static final Logger LOGGER = LoggerFactory.getLogger(FieldModel.class);

  @Override
  protected String doParseToProto(SwaggerToProtoGenerator swaggerToProtoGenerator, Object def) {
    ModelImpl modelImpl = (ModelImpl) def;

    String type = ProtobufConverter.parseSwaggerToProto(modelImpl.getType(), modelImpl.getFormat());
    if (type != null) {
      return type;
    }

//    if (modelImpl.getReference() != null) {
//      return swaggerToProtoGenerator.convertRef(modelImpl.getReference());
//    }

    if (modelImpl.getAdditionalProperties() != null) {
      return MapPropertyProtoConverter.parseToProtobuf(swaggerToProtoGenerator,
          modelImpl.getAdditionalProperties());
    }
    //TODO:: 这个是object
    if (ObjectProperty.TYPE.equals(modelImpl.getType())
        && modelImpl.getProperties() == null
        && modelImpl.getName() == null) {
      return "unSupport:Object";
    }
    LOGGER.error("{} model is unrecognized", modelImpl);
    return "unSupport";
  }
}
