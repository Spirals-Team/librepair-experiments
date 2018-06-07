/*
 * Copyright 2014 The Error Prone Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.errorprone.refaster.testdata.template;

import com.google.errorprone.refaster.annotation.AfterTemplate;
import com.google.errorprone.refaster.annotation.BeforeTemplate;
import java.io.Serializable;

/** Example of a Refaster template using multiply bounded type parameters. */
public class MultiBoundTemplate {
  @BeforeTemplate
  <T extends CharSequence & Serializable> void before(T t) {
    System.out.println(t);
  }

  @AfterTemplate
  <T extends CharSequence & Serializable> void after(T t) {
    System.err.println(t);
  }
}
