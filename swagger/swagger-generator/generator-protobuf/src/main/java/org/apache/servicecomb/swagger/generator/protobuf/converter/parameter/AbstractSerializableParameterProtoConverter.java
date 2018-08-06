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

package org.apache.servicecomb.swagger.generator.protobuf.converter.parameter;

import org.apache.servicecomb.swagger.generator.protobuf.converter.ProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.ProtobufConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.SwaggerToProtoGenerator;
import org.apache.servicecomb.swagger.generator.protobuf.converter.property.ArrayPropertyProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.property.StringPropertyProtoConverter;

import io.swagger.models.parameters.AbstractSerializableParameter;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.StringProperty;

public class AbstractSerializableParameterProtoConverter implements ProtoConverter {

  @Override
  public String parseToProto(SwaggerToProtoGenerator swaggerToClassGenerator, Object def) {
    AbstractSerializableParameter<?> param = (AbstractSerializableParameter<?>) def;

    switch (param.getType()) {
      case ArrayProperty.TYPE:
        return "repeated " + ArrayPropertyProtoConverter.parseToProtoMsg(swaggerToClassGenerator,
            param.getItems());
      case StringProperty.TYPE:
        return StringPropertyProtoConverter.parseToProtoBufMsg(swaggerToClassGenerator,
            param.getType(),
            param.getFormat(),
            param.getEnum());
      default:
        return ProtobufConverter.parseSwaggerToProto(param.getType(), param.getFormat());
    }
  }


}
