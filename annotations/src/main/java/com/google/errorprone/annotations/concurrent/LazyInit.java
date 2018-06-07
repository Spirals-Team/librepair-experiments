/*
 * Copyright 2015 The Error Prone Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.errorprone.annotations.concurrent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation on any static or field that will be initialized lazily, where races yield no
 * semantic difference in the code (as, for example, is the case with {@link String#hashCode}). Note
 * that lazily initializing a non-volatile field is hard to do correctly, and one should rarely use
 * this. It should also only be done by developers who clearly understand the potential issues, and
 * then, always using the pattern as presented in the {@code getData} method of this sample code
 * below:
 *
 * <pre>{@code
 * private final String source;
 * {@literal @}LazyInit private String data;
 *
 * public String getData() {
 *   String local = data;
 *   if (local == null) {
 *     local = data = expensiveCalculation(source);
 *   }
 *   return local;
 *  }
 *
 * private static String expensiveCalculation(String string) {
 *   return string.replaceAll(" ", "_");
 * }
 * }</pre>
 *
 * <p>The need for using the {@code local} variable is detailed in
 * http://jeremymanson.blogspot.com/2008/12/benign-data-races-in-java.html (see, particularly, the
 * part after "Now, let's break the code").
 *
 * <p>Also note that {@code LazyInit} must not be used on 64-bit primitives (such as {@code long}s
 * and {@code double}s), because the Java Language Specification does not guarantee that writing to
 * these is made atomically. Furthermore, when using for non-primitives, the non-primitive must be
 * either truly immutable or at least thread safe (in the Java memory model sense). Again, unless
 * you really understand this <b>and</b> you really need the performance benefits of avoiding the
 * data race, do not use this construct.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LazyInit {}
