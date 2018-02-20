/*
 * Copyright (C) 2017 Seoul National University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.snu.coral.common.ir.edge.executionproperty;

import edu.snu.coral.common.ir.executionproperty.ExecutionProperty;

/**
 * DataCommunicationPattern ExecutionProperty.
 */
public final class DataCommunicationPatternProperty
    extends ExecutionProperty<DataCommunicationPatternProperty.Value>  {
  /**
   * Constructor.
   * @param value value of the execution property.
   */
  private DataCommunicationPatternProperty(final Value value) {
    super(Key.DataCommunicationPattern, value);
  }

  /**
   * Static method exposing the constructor.
   * @param value value of the new execution property.
   * @return the newly created execution property.
   */
  public static DataCommunicationPatternProperty of(final Value value) {
    return new DataCommunicationPatternProperty(value);
  }

  /**
   * Possible values of DataCommunicationPattern ExecutionProperty.
   */
  public enum Value {
    OneToOne,
    BroadCast,
    Shuffle
  }
}
