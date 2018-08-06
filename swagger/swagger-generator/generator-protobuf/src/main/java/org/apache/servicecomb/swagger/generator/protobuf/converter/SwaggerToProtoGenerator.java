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

import java.util.IdentityHashMap;
import java.util.Map;

import org.apache.servicecomb.common.javassist.JavassistUtils;
import org.apache.servicecomb.swagger.converter.ConverterMgr;
import org.apache.servicecomb.swagger.generator.protobuf.protoModel.FieldModel;
import org.apache.servicecomb.swagger.generator.protobuf.protoModel.MessageModel;
import org.apache.servicecomb.swagger.generator.protobuf.protoModel.MessageSwaggerModel;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.annotations.VisibleForTesting;
import javassist.ClassPool;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;

/**
 * generate interface from swagger<br>
 * specially should support: recursive dependency<br>
 * <pre>
 * 1.class A {
 *     A a;
 *   }
 * 2.class A {
 *     B b;
 *   }
 *   class B {
 *     A a;
 *   }
 * </pre>
 * javassist can create normal dynamic class to classloader<br>
 * but can not create recursive dependency dynamic class to classloader directly<br>
 * to support recursive dependency, must save all class to byte[], and then parse to class<br>
 *
 */
public class SwaggerToProtoGenerator {

  private MessageSwaggerModel messageSwaggerModel;

  private Swagger swagger;

  /**
   * package for definitions that no x-java-class attribute
   */
  private String packageName;

  /**
   * if not set, then will get from swagger.info.vendorExtensions.x-java-interface
   * if still not set, then will use ${packageName}.SchemaInterface
   */
  private String interfaceName;


  // key is swagger model or property
  @VisibleForTesting
  protected Map<Object, String> swaggerObjectMap = new IdentityHashMap<>();

  /**
   *
   * @param swagger
   * @param packageName package for definitions that no x-java-class attribute
   */
  public SwaggerToProtoGenerator(Swagger swagger, String packageName) {
    this.swagger = swagger;
    this.packageName = packageName;
    messageSwaggerModel = new MessageSwaggerModel();

  }

  public SwaggerToProtoGenerator(Swagger swagger) {
    this.swagger = swagger;
    messageSwaggerModel = new MessageSwaggerModel();
  }


  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }


  public Swagger getSwagger() {
    return swagger;
  }

  public String getPackageName() {
    return packageName;
  }

  /**
   * parse definitions/parameters and responses to protobuf message
   * parse swagger to protobuf
   */
  public MessageSwaggerModel parseToProtobuf() {
    System.out.println(swagger);
    parseMethodMessage();
    parseDefinitionMessage();
    return messageSwaggerModel;
  }

  private void parseDefinitionMessage() {
    if (swagger.getDefinitions() != null) {
      swagger.getDefinitions().forEach((key, value) -> {
        String name = ((ModelImpl) value).getName();
        MessageModel messageModel = new MessageModel(key);
        if (value.getProperties() != null) {
          value.getProperties().forEach((propertyKey, property) -> {
            FieldModel fieldModel = new FieldModel(property.getName() == null ? propertyKey : property.getName());
            String type = ProtobufConverter.parseSwaggerToProto(this, property);
            fieldModel.setType(type);
            messageModel.addField(fieldModel);
          });
        }
        this.messageSwaggerModel.addDefinitionMessage(messageModel.getMessageName(), messageModel);
      });

    }
  }

  protected void parseMethodMessage() {
    if (swagger.getPaths() != null) {
      swagger.getPaths().forEach((key, path) -> {
        //因为发现有些方法 会有多个所以预防一下,而且查看源码,发现 getOperations() 不可能为 null
        path.getOperations().forEach(operation -> {
          //初始化一个 message
          MessageModel messageModel = new MessageModel(operation.getOperationId());
          for (Parameter parameter : operation.getParameters()) {
            String paramName = parameter.getName();
            FieldModel fieldModel = new FieldModel(paramName);
            //TODO 以后可能需要对 proto 的文件名格式进行规范化,现在先不改
            //          paramName = ClassUtils.correctMethodParameterName(paramName);
            String type = ProtobufConverter.parseSwaggerToProto(this, parameter);
            fieldModel.setType(type);
            messageModel.addField(fieldModel);
          }
          messageSwaggerModel.addMethodMessage(operation.getOperationId(), messageModel);
        });
      });
    }
  }

  public String parse(Object swaggerObject) {
    return ProtobufConverter.parseSwaggerToProto(this, swaggerObject);
  }

}
