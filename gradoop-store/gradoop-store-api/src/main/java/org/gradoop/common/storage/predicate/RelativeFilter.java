/**
 * Copyright © 2014 - 2018 Leipzig University (Database Research Group)
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

package org.gradoop.common.storage.predicate;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.gradoop.common.model.impl.id.GradoopIdSet;

import java.io.Serializable;

/**
 * relative predicate filter,
 */
public interface RelativeFilter extends Serializable {

  /**
   * definition of source vertex id set
   *
   * @return source id set
   */
  @NonNull
  GradoopIdSet getSourceIDSet();

  /**
   * return edge in
   *
   * @return return flag
   */
  boolean enableEdgeIn();

  /**
   * return edge out
   *
   * @return return flag
   */
  boolean enableEdgeOut();

  /**
   * element filter predicate for linked edges
   *
   * @return relative edge element filter
   */
  @Nullable
  ElementFilter getEdgeFilter();

}
