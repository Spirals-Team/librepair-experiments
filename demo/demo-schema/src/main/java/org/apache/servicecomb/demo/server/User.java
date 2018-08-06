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

package org.apache.servicecomb.demo.server;

import java.util.List;
import java.util.Map;

import org.apache.servicecomb.foundation.common.utils.JsonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

public class User {
  private String name = "nameA";

  private int age = 100;

  private int index;

  private String[] names;

  private Map<String, User> stringUserMap;

  private List<User> users;

  private List<List<User>> lists;

  private List<Map<String, User>> maps;

  private Map<String, Map<String, User>> stringMapMap;

  public Map<String, User> getStringUserMap() {
    return stringUserMap;
  }

  public void setStringUserMap(Map<String, User> stringUserMap) {
    this.stringUserMap = stringUserMap;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public List<List<User>> getLists() {
    return lists;
  }

  public void setLists(List<List<User>> lists) {
    this.lists = lists;
  }

  public List<Map<String, User>> getMaps() {
    return maps;
  }

  public void setMaps(List<Map<String, User>> maps) {
    this.maps = maps;
  }

  public Map<String, Map<String, User>> getStringMapMap() {
    return stringMapMap;
  }

  public void setStringMapMap(
      Map<String, Map<String, User>> stringMapMap) {
    this.stringMapMap = stringMapMap;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getNames() {
    return names;
  }

  public void setNames(String[] names) {
    this.names = names;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  @Override
  public String toString() {
    return "User [name=" + name + ", age=" + age + ", index=" + index + "]";
  }

  public String jsonString() {
    try {
      return JsonUtils.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(e);
    }
  }
}
