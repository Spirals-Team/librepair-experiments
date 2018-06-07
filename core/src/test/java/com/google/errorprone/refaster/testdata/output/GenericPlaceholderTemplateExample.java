/*
 * Copyright 2015 The Error Prone Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.errorprone.refaster.testdata;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Example usage of {@code GenericPlaceholderTemplate}.
 */
public class GenericPlaceholderTemplateExample {
  public static void main(String[] args) {
    List<UUID> list = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    list.add(UUID.randomUUID());
}
System.out.println(Joiner.on('\n').join(list));
  }
}
