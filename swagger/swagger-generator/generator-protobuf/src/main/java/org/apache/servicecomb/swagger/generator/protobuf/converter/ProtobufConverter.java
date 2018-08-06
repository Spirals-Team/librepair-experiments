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

package org.apache.servicecomb.swagger.generator.protobuf.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.servicecomb.swagger.generator.protobuf.converter.model.ArrayModelProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.model.ModelImplProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.model.RefModelProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.parameter.AbstractSerializableParameterProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.parameter.BodyParameterProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.property.ArrayPropertyProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.property.MapPropertyProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.property.ObjectPropertyProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.property.RefPropertyProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.converter.property.StringPropertyProtoConverter;
import org.apache.servicecomb.swagger.generator.protobuf.extend.property.ByteProtoProperty;
import org.apache.servicecomb.swagger.generator.protobuf.extend.property.ShortProtoProperty;
import org.apache.servicecomb.swagger.generator.protobuf.protoModel.FieldModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import io.swagger.models.ArrayModel;
import io.swagger.models.ModelImpl;
import io.swagger.models.RefModel;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.CookieParameter;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.BaseIntegerProperty;
import io.swagger.models.properties.BooleanProperty;
import io.swagger.models.properties.ByteArrayProperty;
import io.swagger.models.properties.DateProperty;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.DecimalProperty;
import io.swagger.models.properties.DoubleProperty;
import io.swagger.models.properties.FileProperty;
import io.swagger.models.properties.FloatProperty;
import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.LongProperty;
import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;

public final class ProtobufConverter {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProtobufConverter.class);

  private static final Map<Class<? extends Property>, String> PROPERTY_MAP = new HashMap<>();

  // key为swagger中标准数据类型的type.format
  // value为对应的java class
  private static final Map<String, String> TYPE_FORMAT_MAP = new HashMap<>();

  private static Map<Class<?>, ProtoConverter> converterMap = new HashMap<>();

  static {
    initPropertyMap();
    initTypeFormatMap();
    initConverters();
  }

  public static String genTypeFormatKey(String type, String format) {
    return type + ":" + String.valueOf(format);
  }

  private ProtobufConverter() {

  }

  private static void initTypeFormatMap() {
    try {
      for (Entry<Class<? extends Property>, String> entry : PROPERTY_MAP.entrySet()) {
        Property property = entry.getKey().newInstance();

        String key = genTypeFormatKey(property.getType(), property.getFormat());
        TYPE_FORMAT_MAP.put(key, entry.getValue());
      }
    } catch (Throwable e) {
      throw new Error(e);
    }
  }

  private static void initPropertyMap() {
    PROPERTY_MAP.put(BooleanProperty.class, "bool");

    PROPERTY_MAP.put(FloatProperty.class, "float");
    PROPERTY_MAP.put(DoubleProperty.class, "double");
    PROPERTY_MAP.put(DecimalProperty.class, "int64");

    PROPERTY_MAP.put(ByteProtoProperty.class, "int32");
    PROPERTY_MAP.put(ShortProtoProperty.class, "int32");
    PROPERTY_MAP.put(IntegerProperty.class, "int32");
    PROPERTY_MAP.put(BaseIntegerProperty.class, "int32");
    PROPERTY_MAP.put(LongProperty.class, "int64");

    // stringProperty包含了enum的场景，并不一定是转化为string
    // 但是，如果统一走StringPropertyConverter则可以处理enum的场景
    PROPERTY_MAP.put(StringProperty.class, "string");

    PROPERTY_MAP.put(DateProperty.class, "unSupport:date");
    PROPERTY_MAP.put(DateTimeProperty.class, "unSupport:date-time");

    PROPERTY_MAP.put(ByteArrayProperty.class, "bytes");

    PROPERTY_MAP.put(FileProperty.class, "unSupport:File");
  }

  private static void initConverters() {
    // inner converters
    for (Class<? extends Property> propertyCls : PROPERTY_MAP.keySet()) {
      addInnerConverter(propertyCls);
    }

    converterMap.put(RefProperty.class, new RefPropertyProtoConverter());
    converterMap.put(ArrayProperty.class, new ArrayPropertyProtoConverter());
    converterMap.put(MapProperty.class, new MapPropertyProtoConverter());
    converterMap.put(StringProperty.class, new StringPropertyProtoConverter());
    converterMap.put(ObjectProperty.class, new ObjectPropertyProtoConverter());

    converterMap.put(ModelImpl.class, new ModelImplProtoConverter());
    converterMap.put(RefModel.class, new RefModelProtoConverter());
    converterMap.put(ArrayModel.class, new ArrayModelProtoConverter());

    converterMap.put(BodyParameter.class, new BodyParameterProtoConverter());

    AbstractSerializableParameterProtoConverter converter = new AbstractSerializableParameterProtoConverter();
    converterMap.put(QueryParameter.class, converter);
    converterMap.put(PathParameter.class, converter);
    converterMap.put(HeaderParameter.class, converter);
    converterMap.put(FormParameter.class, converter);
    converterMap.put(CookieParameter.class, converter);
  }

  private static void addInnerConverter(Class<? extends Property> propertyCls) {
    String type = PROPERTY_MAP.get(propertyCls);
    if (type == null) {
      throw new Error("not support inner property class: " + propertyCls.getName());
    }

    converterMap.put(propertyCls, (context, def) -> type);
  }


  public static String parseSwaggerToProto(String type, String format) {
    String key = genTypeFormatKey(type, format);
    return TYPE_FORMAT_MAP.get(key);
  }

  // def为null是void的场景
  // def可能是model、property、parameter
  public static String parseSwaggerToProto(SwaggerToProtoGenerator swaggerToProtoGenerator, Object def) {
    if (def == null) {
      LOGGER.error("the property or parameter can not be null");
      return "unSupport:null";
    }
    ProtoConverter converter = converterMap.get(def.getClass());
    if (converter == null) {
      throw new Error("not support def type: " + def.getClass());
    }

    return converter.parseToProto(swaggerToProtoGenerator, def);
  }

}
