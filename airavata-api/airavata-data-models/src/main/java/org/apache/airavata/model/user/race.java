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
package org.apache.airavata.model.user;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * * Asian - a person having origins in any of the original peoples of the Far East,
 * *      Southeast Asia, or the Indian subcontinent including, for example, Cambodia,
 *  *      China, India, Japan, Korea, Malaysia, Pakistan, the Philippine Islands,
 *  *      Thailand, and Vietnam.
 * * American Indian or Alaskan Native - a person having origins in any of the original
 *  *     peoples of North and South America (including Central America), and who maintains
 *   *     tribal affiliation or community attachment.
 * * Black or African American - a person having origins in any of the black racial groups
 * *      of Africa.
 * * Native Hawaiian or Pacific Islander - a person having origins in any of the original
 * *      peoples of Hawaii, Guan, Samoa, or other Pacific Islands.
 * * White - a person having origins in any of the original peoples of Europe, the Middle East, or North Africa.
 * *
 */
public enum race implements org.apache.thrift.TEnum {
  ASIAN(0),
  AMERICAN_INDIAN_OR_ALASKAN_NATIVE(1),
  BLACK_OR_AFRICAN_AMERICAN(2),
  NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER(3),
  WHITE(4);

  private final int value;

  private race(int value) {
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
  public static race findByValue(int value) { 
    switch (value) {
      case 0:
        return ASIAN;
      case 1:
        return AMERICAN_INDIAN_OR_ALASKAN_NATIVE;
      case 2:
        return BLACK_OR_AFRICAN_AMERICAN;
      case 3:
        return NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER;
      case 4:
        return WHITE;
      default:
        return null;
    }
  }
}
