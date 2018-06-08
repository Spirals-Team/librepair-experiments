/*
 * Copyright (c) 2018 Red Hat, Inc.
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

package io.vertx.junit5;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Specify how long {@link VertxTestContext#awaitCompletion(long, TimeUnit)} waits before timing out.
 * <p>
 * This annotation works on both test methods and test classes.
 *
 * @author <a href="https://julien.ponge.org/">Julien Ponge</a>
 * @see VertxTestContext
 * @see org.junit.jupiter.api.Test
 * @see org.junit.jupiter.api.extension.ExtendWith
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Timeout {
  int value();

  TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
