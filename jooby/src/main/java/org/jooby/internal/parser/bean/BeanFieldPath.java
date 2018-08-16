/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jooby.internal.parser.bean;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

class BeanFieldPath implements BeanPath {
  private String path;

  private Field field;

  public BeanFieldPath(final String path, final Field field) {
    this.path = path;
    this.field = field;
    this.field.setAccessible(true);
  }

  @Override
  public void set(final Object bean, final Object... args)
      throws Throwable {
    field.set(bean, args[0]);
  }

  @Override
  public Object get(final Object bean, final Object... args) throws Throwable {
    return field.get(bean);
  }

  @Override
  public Type type() {
    return field.getGenericType();
  }

  @Override
  public AnnotatedElement setelem() {
    return field;
  }

  @Override
  public String toString() {
    return path;
  }
}