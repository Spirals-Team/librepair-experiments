/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.table.descriptors

import java.util

import org.apache.flink.api.common.typeinfo.BasicTypeInfo

import scala.collection.JavaConverters._

class ClassTypeTest extends DescriptorTestBase {

  override def descriptors(): util.List[Descriptor] = {
    val desc1 = ClassTypeDescriptor()
      .setClassName("class1")
      .addConstructorField(
        PrimitiveTypeDescriptor()
          .setType(BasicTypeInfo.LONG_TYPE_INFO)
          .setValue(1L))
      .addConstructorField(
        ClassTypeDescriptor()
          .setClassName("class2")
          .addConstructorField(
            ClassTypeDescriptor()
              .setClassName("class3")
              .addConstructorField(
                PrimitiveTypeDescriptor()
                  .setType(BasicTypeInfo.STRING_TYPE_INFO)
                  .setValue("StarryNight"))
              .addConstructorField(
                ClassTypeDescriptor()
                  .setClassName("class4"))))

    util.Arrays.asList(desc1)
  }

  override def validator(): DescriptorValidator = {
    new FunctionValidator()
  }

  override def properties(): util.List[util.Map[String, String]] = {
    val props1 = Map(
      "class" -> "class1",
      "constructor.0.type" -> "BIGINT",
      "constructor.0.value" -> "1",
      "constructor.1.class" -> "class2",
      "constructor.1.constructor.0.class" -> "class3",
      "constructor.1.constructor.0.constructor.0.type" -> "VARCHAR",
      "constructor.1.constructor.0.constructor.0.value" -> "StarryNight",
      "constructor.1.constructor.0.constructor.1.class" -> "class4"
    )
    util.Arrays.asList(props1.asJava)
  }
}
