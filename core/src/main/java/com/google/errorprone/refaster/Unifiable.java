/*
 * Copyright 2013 The Error Prone Authors.
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

package com.google.errorprone.refaster;

import java.io.Serializable;

/**
 * A serializable representation of a template that can be matched against a target of type {@code
 * T}.
 *
 * @author Louis Wasserman
 */
public interface Unifiable<T> extends Serializable {
  /**
   * Returns all valid unification paths (if any) from this {@code Unifier} that unify this with
   * {@code target}.
   */
  Choice<Unifier> unify(T target, Unifier unifier);
}
