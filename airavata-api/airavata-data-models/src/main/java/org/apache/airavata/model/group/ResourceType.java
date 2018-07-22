/**
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

/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.airavata.model.group;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum ResourceType implements org.apache.thrift.TEnum {
  PROJECT(0),
  EXPERIMENT(1),
  DATA(2),
  APPLICATION_DEPLOYMENT(3),
  GROUP_RESOURCE_PROFILE(4),
  OTHER(5);

  private final int value;

  private ResourceType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static ResourceType findByValue(int value) { 
    switch (value) {
      case 0:
        return PROJECT;
      case 1:
        return EXPERIMENT;
      case 2:
        return DATA;
      case 3:
        return APPLICATION_DEPLOYMENT;
      case 4:
        return GROUP_RESOURCE_PROFILE;
      case 5:
        return OTHER;
      default:
        return null;
    }
  }
}
